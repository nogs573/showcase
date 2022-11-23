
import sqlite3
import traceback
import os
import urllib.parse

SPELL_FILE = 'Spell.csv'
MONSTER_FILE = 'Creature.csv'
WEAPON_FILE = 'Weapon.csv'
PLAYERCLASS_FILE = 'PlayerClass.csv'
DAMAGETYPE_FILE = 'DamageType.csv'
WEAPONDMGTYPE_FILE = 'WeapDmgType.csv'
SPELLDMGTYPE_FILE = 'SpellDmgType.csv'
CLASS_SAVINGTHROWS_FILE = 'ClassSTs.csv'
CAN_USE_WEAPON_FILE = 'CanUseWeapon.csv'
CAN_USE_SPELL_FILE = 'CanUseSpell.csv'
CREATURE_SAVINGTHROWS_FILE = 'CreatureSTs.csv'
IMMUNETO_FILE = 'ImmuneTo.csv'
WEAKTO_FILE = 'WeakTo.csv'
RESIST_FILE = 'Resists.csv'
SENSES_FILE = 'Senses.csv'
SKILL_FILE = 'Skills.csv'
SPEED_FILE = 'Speeds.csv'
LANGUAGE_FILE = 'Languages.csv'
FEATURE_FILE = 'AdditionalFeatures.csv'


class MyDatabase:
    def __init__(self):
        self.cur = None
        try:
            self.con = sqlite3.connect('DAndD.db')
            self.con.execute('PRAGMA foreign_keys = 1')
            self.cur = self.con.cursor()
            
            path = os.getcwd()
            path += '\DAndD.db'
            if not os.path.exists(path):
                self.createTables()
            self.insertData()

        except Exception as e:
            print(e)
    
    def __del__(self):
        #os.remove("DAndD.db")
        pass

    def close(self):
        self.con.close()

    def createTables(self):
        try:
            self.cur.execute('''CREATE TABLE Creature( 
                CreatureName VARCHAR(60) primary key, Size VARCHAR(15), Type VARCHAR(30),
                Alignment VARCAHR(10), AC INTEGER, HP INTEGER, STR INTEGER, DEX INTEGER,
                CON INTEGER, INT INTEGER, WIS INTEGER, CHA INTEGER, CR REAL,
                SourceBook VARCHAR(40)
            )''') 
            #INT may need change to CINT, I don't know if it will cause an issue because INT is reserverd in SQLite

            self.cur.execute('''CREATE TABLE Speeds(
                CreatureName VARCHAR(60), Speed VARCHAR(50),
                PRIMARY KEY(CreatureName, Speed),
                FOREIGN KEY (CreatureName) references Creature on delete cascade
            )''' )

            self.cur.execute('''CREATE TABLE CreatureSTs(
                CreatureName VARCHAR(60), SavingThrow VARCHAR(5),
                PRIMARY KEY(CreatureName, SavingThrow),
                FOREIGN KEY (CreatureName) references Creature on delete cascade
            )''')

            self.cur.execute('''CREATE TABLE Skills(
                CreatureName VARCHAR(60), Skill VARCHAR(15),
                PRIMARY KEY(CreatureName, Skill),
                FOREIGN KEY (CreatureName) references Creature on delete cascade
            )''')

            self.cur.execute('''CREATE TABLE Senses(
                CreatureName VARCHAR(60), Sense VARCHAR(20),
                PRIMARY KEY(CreatureName, Sense),
                FOREIGN KEY (CreatureName) references Creature on delete cascade
            )''')

            self.cur.execute('''CREATE TABLE Languages(
                CreatureName VARCHAR(60), Language VARCHAR(20),
                PRIMARY KEY(CreatureName, Language),
                FOREIGN KEY (CreatureName) references Creature on delete cascade
            )''')

            self.cur.execute('''CREATE TABLE AdditionalFeature(
                CreatureName VARCHAR(60), Feature VARCHAR(60),
                PRIMARY KEY(CreatureName, Feature),
                FOREIGN KEY (CreatureName) references Creature on delete cascade
            )''')

            self.cur.execute('''CREATE TABLE DamageType(
                DamageName VARCHAR(11) PRIMARY KEY
            )''')

            self.cur.execute('''CREATE TABLE Spell(
                SpellName VARCHAR(50) PRIMARY KEY, Level INTEGER, Ritual VARCHAR(6),
                CastingTime INTEGER, Range INTEGER, Verbal VARCHAR(1), 
                Somatic VARCHAR(1), Material VARCHAR(1), Concentration VARCHAR(13),
                Duration INTEGER, DamageDice VARCHAR(30), MaxDamage INTEGER, SourceBook VARCHAR(5),
                Description VARCHAR(500)
            )''')

            self.cur.execute('''CREATE TABLE Weapon(
                WeaponName VARCHAR(20) PRIMARY KEY, SimpleMartial VARCHAR(7),
                MeleeRanged VARCHAR(6), WeaponGroup VARCHAR(8), DamageDice VARCHAR(30), MaxDamage INTEGER,
                Versatile VARCHAR(5), Light VARCHAR(1), Heavy VARCHAR(1),
                Range varchar(8), Thrown VARCHAR(1), Finesse VARCHAR(1), Reach VARCHAR(1)
            )''')
            
            #No idea what this is
            self.cur.execute('''CREATE TABLE PlayerClass(
                ClassName VARCHAR(9) PRIMARY KEY, HitDice VARCHAR(4),
                LightArmor VARCHAR(1), MediumArmor VARCHAR(1), HeavyArmor VARCHAR(1),
                Shield VARCHAR(1)
            )''')

            self.cur.execute('''CREATE TABLE ClassSTs(
                ClassName VARCHAR(60),  
                SavingThrow VARCHAR(60),
                PRIMARY KEY (ClassName,SavingThrow)
                FOREIGN KEY (ClassName) references PlayerClass on delete cascade
            )''')

            self.cur.execute('''CREATE TABLE WeakTo(
                CreatureName VARCHAR(60),
                DamageName VARCHAR(60),
                PRIMARY KEY (CreatureName, DamageName),
                FOREIGN KEY (CreatureName) references Creature on delete cascade,
                FOREIGN KEY (DamageName) references DamageType on delete cascade
            )''')

            self.cur.execute('''CREATE TABLE ImmuneTo(
                CreatureName VARCHAR(60),
                DamageName VARCHAR(60),
                PRIMARY KEY (CreatureName, DamageName),
                FOREIGN KEY (CreatureName) references Creature on delete cascade,
                FOREIGN KEY (DamageName) references DamageType on delete cascade
            )''')

            self.cur.execute('''CREATE TABLE Resists(
                CreatureName VARCHAR(60),
                DamageName VARCHAR(60),
                PRIMARY KEY (CreatureName, DamageName),
                FOREIGN KEY (CreatureName) references Creature on delete cascade,
                FOREIGN KEY (DamageName) references DamageType on delete cascade
            )''')

            self.cur.execute('''CREATE TABLE SpellDmgType(
                SpellName VARCHAR(50),
                DamageName VARCHAR(60),
                PRIMARY KEY (SpellName, DamageName),
                FOREIGN KEY (SpellName) references Spell on delete cascade,
                FOREIGN KEY (DamageName) references DamageType on delete cascade
            )''')

            self.cur.execute('''CREATE TABLE WeaponDmgType(
                WeaponName VARCHAR(20),
                DamageName VARCHAR(60),
                PRIMARY KEY (WeaponName, DamageName),
                FOREIGN KEY (WeaponName) references Weapon on delete cascade,
                FOREIGN KEY (DamageName) references DamageType on delete cascade
            )''')

            self.cur.execute('''CREATE TABLE CanUseWeapon(
                ClassName VARCHAR(60),
                WeaponName VARCHAR(20),
                PRIMARY KEY (ClassName, WeaponName),
                FOREIGN KEY (ClassName) references PlayerClass on delete cascade,
                FOREIGN KEY (WeaponName) references Weapon on delete cascade
            )''')

            self.cur.execute('''CREATE TABLE CanUseSpell(
                ClassName VARCHAR(60),
                SpellName VARCHAR(20),
                PRIMARY KEY (ClassName, SpellName),
                FOREIGN KEY (ClassName) references PlayerClass on delete cascade,
                FOREIGN KEY (SpellName) references Spell on delete cascade
            )''')

            self.con.commit()
        except Exception as e:
            print(e)
            print(traceback.format_exc())

    def insertData(self):
        lines = ""
        try:
            #Insert Weapon------------------------------------
            with open(WEAPON_FILE) as file:
                lines = file.readlines()
            insertSQL = "insert into Weapon (WeaponName, SimpleMartial,MeleeRanged,WeaponGroup,DamageDice,MaxDamage," 
            insertSQL += "Versatile,Light,Heavy,Range,Thrown,Finesse,Reach) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);"

            self.insertLine(insertSQL,lines)
            lines = ""
            file.close()

            #Insert PlayerClass------------------------------------
            with open(PLAYERCLASS_FILE) as file:
                lines = file.readlines()
            insertSQL = "insert into PlayerClass (ClassName,HitDice,LightArmor,MediumArmor,HeavyArmor,"
            insertSQL += "Shield) VALUES (?,?,?,?,?,?); "

            self.insertLine(insertSQL,lines)
            lines = ""
            file.close()

            #Insert Spell-------------------------------------------
            with open(SPELL_FILE) as file:
                lines = file.readlines()
            insertSQL = "insert into Spell (SpellName,Level,Ritual,CastingTime,Range,Verbal,Somatic,"
            insertSQL += "Material,Concentration,Duration,DamageDice,MaxDamage,SourceBook,Description) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);"
            self.insertLine(insertSQL,lines)
            lines = ""

            #Insert Creature------------------------------------
            with open(MONSTER_FILE) as file:
                lines = file.readlines()
            insertSQL = "insert into Creature (CreatureName,Size,Type,Alignment,AC,HP,STR,DEX,CON,INT,WIS,CHA,CR,SourceBook"
            insertSQL += ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?); "
            self.insertLine(insertSQL,lines) 
            lines = ""    
            file.close()    

            #Insert DamageType----------------------------------------
            with open(DAMAGETYPE_FILE) as file:
                lines = file.readlines()    
            insertSQL = "insert into DamageType (DamageName) VALUES (?)"
            self.insertLine(insertSQL,lines) 
            lines = ""    
            file.close() 

            #Insert WeaponDamageType---------------------------------
            with open(WEAPONDMGTYPE_FILE) as file:
                lines = file.readlines()    
            insertSQL = "insert into WeaponDmgType (WeaponName,DamageName) VALUES (?,?)"
            self.insertLine(insertSQL,lines)   
            lines = ""
            file.close() 

            #Insert SpellDamageType---------------------------------
            with open(SPELLDMGTYPE_FILE) as file:
                lines = file.readlines()    
            insertSQL = "insert into SpellDmgType (SpellName,DamageName) VALUES (?,?)"
            self.insertLine(insertSQL,lines) 

            lines = ""    
            file.close() 

            #Insert PlayerClass SavingThrow---------------------------------
            with open(CLASS_SAVINGTHROWS_FILE) as file:
                lines = file.readlines()    
            insertSQL = "insert into ClassSTs (ClassName,SavingThrow) VALUES (?,?)"
            self.insertLine(insertSQL,lines) 
            lines = ""    
            file.close() 

            #Insert Creature SavingThrow---------------------------------
            with open(CREATURE_SAVINGTHROWS_FILE) as file:
                lines = file.readlines()    
            insertSQL = "insert into CreatureSTs (CreatureName,SavingThrow) VALUES (?,?)"
            self.insertLine(insertSQL,lines) 
            lines = ""    
            file.close() 

            #Insert Can Use Weapon---------------------------------
            with open(CAN_USE_WEAPON_FILE) as file:
                lines = file.readlines()    
            insertSQL = "insert into CanUseWeapon (ClassName,WeaponName) VALUES (?,?)"
            self.insertLine(insertSQL,lines) 
            lines = ""    
            file.close() 

            #Insert Can Use Spell---------------------------------
            with open(CAN_USE_SPELL_FILE) as file:
                lines = file.readlines()    
            insertSQL = "insert into CanUseSpell (SpellName,ClassName) VALUES (?,?)"
            self.insertLine(insertSQL,lines) 
            lines = ""    
            file.close() 

            #Insert ImmuneTo---------------------------------
            with open(IMMUNETO_FILE) as file:
                lines = file.readlines()    
            insertSQL = "insert into ImmuneTo (CreatureName,DamageName) VALUES (?,?)"
            self.insertLine(insertSQL,lines) 
            lines = ""    
            file.close() 

            #Insert WeakTo---------------------------------
            with open(WEAKTO_FILE) as file:
                lines = file.readlines()    
            insertSQL = "insert into WeakTo (CreatureName,DamageName) VALUES (?,?)"
            self.insertLine(insertSQL,lines) 
            lines = ""    
            file.close() 

            #Insert Resists---------------------------------
            with open(RESIST_FILE) as file:
                lines = file.readlines()    
            insertSQL = "insert into Resists (CreatureName,DamageName) VALUES (?,?)"
            self.insertLine(insertSQL,lines) 
            lines = ""    
            file.close() 

            #Insert Senses---------------------------------
            with open(SENSES_FILE) as file:
                lines = file.readlines()    
            insertSQL = "insert into Senses (CreatureName,Sense) VALUES (?,?)"
            self.insertLine(insertSQL,lines) 
            lines = ""    
            file.close() 

            #Insert Skills---------------------------------
            with open(SKILL_FILE) as file:
                lines = file.readlines()    
            insertSQL = "insert into Skills (CreatureName,Skill) VALUES (?,?)"
            self.insertLine(insertSQL,lines) 
            lines = ""    
            file.close() 

            #Insert Speeds---------------------------------
            with open(SPEED_FILE) as file:
                lines = file.readlines()    
            insertSQL = "insert into Speeds (CreatureName,Speed) VALUES (?,?)"
            self.insertLine(insertSQL,lines) 
            lines = ""    
            file.close() 

            #Insert Languages---------------------------------
            with open(LANGUAGE_FILE) as file:
                lines = file.readlines()    
            insertSQL = "insert into Languages (CreatureName,Language) VALUES (?,?)"
            self.insertLine(insertSQL,lines) 
            lines = ""    
            file.close()

            #Insert Additional Feature---------------------------------
            with open(FEATURE_FILE) as file:
                lines = file.readlines()    
            insertSQL = "insert into AdditionalFeature (CreatureName,Feature) VALUES (?,?)"
            self.insertLine(insertSQL,lines) 
            lines = ""    
            file.close()

            
        except Exception as e:
            print(e)
            print(traceback.format_exc())

    def insertLine(self,sql,lines):
        for row in lines[1:]:
            row = row.strip().split(",")
            self.cur.execute(sql,row)  
        
    def manipulateTable(self,keys):
        sql = 'SELECT '
        i=0
        length=len(keys)
        while i<length-5:
            if(i==length-6):
                sql+=urllib.parse.unquote(keys[i])
            else:
                sql+=urllib.parse.unquote(keys[i])+','
            i+=1
        sql+=' FROM '+urllib.parse.unquote(keys[i])
        if(keys[length-1]=='true'):
            sql+=' ORDER BY '+ urllib.parse.unquote(keys[i+1])
            sql+=' '+ urllib.parse.unquote(keys[i+2])
        sql+=' LIMIT '+ urllib.parse.unquote(keys[i+3])+';'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result
    

    def getSortedTable(self,keys):
        sql = 'SELECT '
        i=0
        length=len(keys)
        while i<length-5:
            if(i==length-6):
                sql+=urllib.parse.unquote(keys[i])
            else:
                sql+=urllib.parse.unquote(keys[i])+','
            i+=1
        sql+=' FROM '+urllib.parse.unquote(keys[i])
        sql+=' ORDER BY '
        sql+= urllib.parse.unquote(keys[i+1])+' '
        sql+= urllib.parse.unquote(keys[i+2])+' '
        sql+=' LIMIT '+urllib.parse.unquote(keys[i+3])+';'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result   

    # --------------- GET MAIN TABLE -------------------

    def getCreature(self):
        sql = 'SELECT * FROM Creature;'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result

    def getWeapon(self):
        sql = 'SELECT * FROM Weapon;'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result

    def getSpell(self):
        sql = 'SELECT * FROM Spell;'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result

    def getPlayerClass(self):
        sql = 'SELECT * FROM PlayerClass;'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result

    def getDamageType(self):
        sql = 'SELECT * FROM DamageType'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result

    # ----------------- GET SUPPORT TABLES -------------------

    def getAdditionalFeats(self):
        sql = 'SELECT * FROM AdditionalFeature'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result

    def getCanUseSpell(self):
        sql = 'SELECT * FROM CanUseSpell'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result
    
    def getCanUseWeapon(self):
        sql = 'SELECT * FROM CanUseWeapon'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result

    def getClassSTs(self):
        sql = 'SELECT * FROM ClassSTs'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result

    def getCreatureSTs(self):
        sql = 'SELECT * FROM CreatureSTs'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result
    
    def getImmuneTo(self):
        sql = 'SELECT * FROM ImmuneTo'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result
    
    def getLanguages(self):
        sql = 'SELECT * FROM Languages'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result

    def getResists(self):
        sql = 'SELECT * FROM Resists'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result
    
    def getSenses(self):
        sql = 'SELECT * FROM Senses'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result

    def getSkills(self):
        sql = 'SELECT * FROM Skills'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result
    
    def getSpeeds(self):
        sql = 'SELECT * FROM Speeds'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result

    def getSpellDmgType(self):
        sql = 'SELECT * FROM SpellDmgType'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result
    
    def getWeakTo(self):
        sql = 'SELECT * FROM WeakTo'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result
    
    def getWeapDmgType(self):
        sql = 'SELECT * FROM WeaponDmgType'
        result = self.cur.execute(sql)
        result = self.convert(result)
        return result    

    def convert(self,result):
        r = [dict((self.cur.description[i][0], value) \
            for i, value in enumerate(row)) for row in result.fetchall()]
        return r

    # -------------------------- MVA Checkbox Alert SQL ---------------------------------
    
    def getWeaponInfo(self,weapName):
        weapName = urllib.parse.unquote(weapName)
        result = self.cur.execute("SELECT DamageName FROM WeaponDmgType WHERE WeaponName = '%s' " % weapName)
        result = self.convert(result)
        return result

    def getSpellInfo(self,spellName):
        spellName = urllib.parse.unquote(spellName)
        result = self.cur.execute("SELECT DamageName FROM SpellDmgType WHERE SpellName = '%s' " % spellName)
        result = self.convert(result)
        return result

    def getPlayerInfoWeap(self,className):
        className = urllib.parse.unquote(className)
        result = self.cur.execute("SELECT WeaponName FROM CanUseWeapon WHERE ClassName = '%s' " % className)
        result = self.convert(result)
        return result

    def getPlayerInfoSpell(self,className):
        className = urllib.parse.unquote(className)
        result = self.cur.execute("SELECT SpellName FROM CanUseSpell WHERE ClassName = '%s' " % className)
        result = self.convert(result)
        return result

    def getPlayerSTInfo(self,className):
        className = urllib.parse.unquote(className)
        result = self.cur.execute("SELECT SavingThrow FROM ClassSTs WHERE ClassName = '%s' " % className)
        result = self.convert(result)
        return result

    def getCreatureSTInfo(self,creatureName):
        creatureName = urllib.parse.unquote(creatureName)
        result = self.cur.execute("SELECT SavingThrow FROM CreatureSTs WHERE CreatureName = '%s' " % creatureName)
        result = self.convert(result)
        return result

    def getCreatureSkillInfo(self,creatureName):
        creatureName = urllib.parse.unquote(creatureName)
        result = self.cur.execute("SELECT Skill FROM Skills WHERE CreatureName = '%s' " % creatureName)
        result = self.convert(result)
        return result

    def getCreatureImmuneInfo(self,creatureName):
        creatureName = urllib.parse.unquote(creatureName)
        result = self.cur.execute("SELECT DamageName FROM ImmuneTo WHERE CreatureName = '%s' " % creatureName)
        result = self.convert(result)
        return result

    def getCreatureWeakInfo(self,creatureName):
        creatureName = urllib.parse.unquote(creatureName)
        result = self.cur.execute("SELECT DamageName FROM WeakTo WHERE CreatureName = '%s' " % creatureName)
        result = self.convert(result)
        return result

    def getCreatureResistInfo(self,creatureName):
        creatureName = urllib.parse.unquote(creatureName)
        result = self.cur.execute("SELECT DamageName FROM Resists WHERE CreatureName = '%s' " % creatureName)
        result = self.convert(result)
        return result
    
    def getCreatureLanguageInfo(self,creatureName):
        creatureName = urllib.parse.unquote(creatureName)
        result = self.cur.execute("SELECT Language FROM Languages WHERE CreatureName = '%s' " % creatureName)
        result = self.convert(result)
        return result

    def getCreatureSenseInfo(self,creatureName):
        creatureName = urllib.parse.unquote(creatureName)
        result = self.cur.execute("SELECT Sense FROM Senses WHERE CreatureName = '%s' " % creatureName)
        result = self.convert(result)
        return result

    def getCreatureSpeedInfo(self,creatureName):
        creatureName = urllib.parse.unquote(creatureName)
        result = self.cur.execute("SELECT Speed FROM Speeds WHERE CreatureName = '%s' " % creatureName)
        result = self.convert(result)
        return result

    def getCreatureAddFeature(self,creatureName):
        creatureName = urllib.parse.unquote(creatureName)
        result = self.cur.execute("SELECT Feature FROM AdditionalFeature WHERE CreatureName = '%s' " % creatureName)
        result = self.convert(result)
        return result

#hard coding query
    def getPreWrittenQuery1(self):
        queryStr = "SELECT PlayerClass.ClassName, CanUseWeapon.WeaponName, CanUseSpell.SpellName FROM PlayerClass " 
        queryStr += "JOIN CanUseWeapon on PlayerClass.ClassName = CanUseWeapon.ClassName "
        queryStr += "JOIN WeaponDmgType on CanUseWeapon.WeaponName = WeaponDmgType.WeaponName "
        queryStr += "JOIN CanUseSpell on PlayerClass.ClassName = CanUseSpell.ClassName  "
        queryStr += "JOIN SpellDmgType on CanUseSpell.SpellName = SpellDmgType.SpellName "
        queryStr += "WHERE PlayerClass.ClassName = 'Druid' AND WeaponDmgType.DamageName in ("
        queryStr += "SELECT DamageName FROM WeakTo WHERE CreatureName = 'Ice Mephit' ) AND "
        queryStr += "SpellDmgType.DamageName in ("
        queryStr += "SELECT DamageName FROM WeakTo WHERE CreatureName = 'Ice Mephit' )"
        result = self.cur.execute(queryStr)
        result = self.convert(result)
        return result

    
    def getPreWrittenQuery2(self):
        queryStr = "SELECT SpellName, Verbal, DamageDice, MaxDamage from Spell "
        queryStr += "WHERE Somatic = 'N' and MaxDamage > 0 "
        queryStr += "ORDER BY MaxDamage DESC"
        result = self.cur.execute(queryStr)
        result = self.convert(result)
        return result
        
    def getPreWrittenQuery3(self):
        queryStr = "SELECT DamageName, count(SpellDmgType.SpellName) as NumSpell from Spell "
        queryStr += "JOIN SpellDmgType on Spell.SpellName = SpellDmgType.SpellName "
        queryStr += "WHERE Level>=5 GROUP BY DamageName ORDER BY NumSpell DESC"
        result = self.cur.execute(queryStr)
        result = self.convert(result)
        return result
        
    def getPreWrittenQuery4(self):
        queryStr = "SELECT DISTINCT C.CreatureName, CR, HP, AC, STR, DEX, CON, INT, WIS, CHA FROM Creature C "
        queryStr += "JOIN Resists on C.CreatureName = Resists.CreatureName "
        queryStr += "WHERE CR<5 and HP < 100 AND EXISTS  "
        queryStr += " ( SELECT Resists.CreatureName FROM Resists WHERE Resists.CreatureName = C.CreatureName AND DamageName = 'Slashing') "
        queryStr += " AND EXISTS ( SELECT Resists.CreatureName FROM Resists WHERE Resists.CreatureName = C.CreatureName AND DamageName = 'Bludgeoning') "
        queryStr += " AND EXISTS ( SELECT Resists.CreatureName FROM Resists WHERE Resists.CreatureName = C.CreatureName AND DamageName = 'Piercing') "
        result = self.cur.execute(queryStr)
        result = self.convert(result)
        return result
        
    def getPreWrittenQuery5(self):
        queryStr = "SELECT DamageName, count(Creature.CreatureName) as NumImmune FROM Creature "
        queryStr += "JOIN ImmuneTo on Creature.CreatureName = ImmuneTo.CreatureName "
        queryStr += "GROUP BY DamageName Order By NumImmune DESC"
        result = self.cur.execute(queryStr)
        result = self.convert(result)
        return result
        
    def getPreWrittenQuery6(self):
        queryStr = "SELECT PlayerClass.ClassName, sum(CASE WHEN DamageName = 'Fire' then 1 else 0 end) as FireSpells, "
        queryStr += "sum(CASE WHEN DamageName = 'Cold' then 1 else 0 end) as ColdSpells, "
        queryStr += "sum(CASE WHEN DamageName = 'Cold' or DamageName = 'Fire' then 1 else 0 end) as total FROM PlayerClass "
        queryStr += "JOIN CanUseSpell on PlayerClass.ClassName = CanUseSpell.ClassName "
        queryStr += "JOIN SpellDmgType on CanUseSpell.SpellName = SpellDmgType.SpellName "
        queryStr += "GROUP BY PlayerClass.ClassName order by total DESC"
        result = self.cur.execute(queryStr)
        result = self.convert(result)
        return result
        
    def getPreWrittenQuery7(self):
        queryStr = "SELECT PlayerClass.ClassName, count(DISTINCT WeaponName) as NumWeaps, "
        queryStr += "count(DISTINCT SpellName) as NumSpells, (count(DISTINCT WeaponName) + count(DISTINCT SpellName)) as Total From PlayerClass "
        queryStr += "JOIN CanUseSpell on PlayerClass.ClassName = CanUseSpell.ClassName "
        queryStr += "JOIN CanUseWeapon on PlayerClass.ClassName = CanUseWeapon.ClassName "
        queryStr += "GROUP BY PlayerClass.ClassName ORDER BY Total DESC limit 5"
        result = self.cur.execute(queryStr)
        result = self.convert(result)
        return result
        
    def getPreWrittenQuery8(self):
        queryStr = "SELECT CreatureName, AC, HP, STR, DEX, CON, INT, WIS, CHA, "
        queryStr += " (AC+HP+STR+DEX+CON+INT+WIS+CHA) as SumStats FROM Creature "
        queryStr += "ORDER BY SumStats DESC limit 10"
        result = self.cur.execute(queryStr)
        result = self.convert(result)
        return result
        
    def getPreWrittenQuery9(self):
        queryStr = "SELECT Creature.CreatureName, count(DISTINCT WeaponName) as WeapResistNum, count(DISTINCT SpellName) as SpellResistNum, "
        queryStr += "(count(DISTINCT WeaponName) + count(DISTINCT SpellName)) as totalResist From Creature "
        queryStr += "JOIN Resists on Creature.CreatureName = Resists.CreatureName "
        queryStr += "JOIN WeaponDmgType on WeaponDmgType.DamageName = Resists.DamageName "
        queryStr += "JOIN SpellDmgType on SpellDmgType.DamageName = Resists.DamageName "
        queryStr += "Group By Creature.CreatureName "
        queryStr += "ORDER BY totalResist DESC "
        result = self.cur.execute(queryStr)
        result = self.convert(result)
        return result
        
    def getPreWrittenQuery10(self):
        queryStr = "SELECT CASE "
        queryStr += " WHEN HP Between 1 AND 20 THEN 'HP:1-20' "
        queryStr += " WHEN HP Between 21 AND 40 THEN 'HP:21-40' "
        queryStr += " WHEN HP Between 41 AND 60 THEN 'HP:41-60' "
        queryStr += " WHEN HP Between 61 AND 80 THEN 'HP:61-80' "
        queryStr += " WHEN HP Between 81 AND 100 THEN 'HP:81-100' "
        queryStr += " WHEN HP Between 101 AND 120 THEN 'HP:101-120' "
        queryStr += " WHEN HP Between 121 AND 140 THEN 'HP:121-140' "
        queryStr += " WHEN HP Between 141 AND 160 THEN 'HP:141-160' "
        queryStr += " WHEN HP Between 161 AND 180 THEN 'HP:161-180' "
        queryStr += " WHEN HP Between 181 AND 200 THEN 'HP:181-200' "
        queryStr += " ELSE 'HP>200' END as Range, count(*) as Number FROM Creature Group By Range Order By Range"
        result = self.cur.execute(queryStr)
        result = self.convert(result)
        return result

    def getPreWrittenQuery11(self):
        queryStr = "SELECT Type, AVG( CAST(Speed AS INTEGER) ) as AVG_Speed "
        queryStr += "FROM Creature JOIN Speeds on Creature.CreatureName = Speeds.CreatureName "
        queryStr += "Group By Type order by AVG_Speed "
        result = self.cur.execute(queryStr)
        result = self.convert(result)
        return result

    def getPreWrittenQuery12(self, keys):
        creatureName=""
        level=""
        if(len(keys)==1):
            level=urllib.parse.unquote(keys[0])
        if(len(keys)==2):
            level=urllib.parse.unquote(keys[0])
            creatureName=urllib.parse.unquote(keys[1])
        immune = "SELECT DamageName FROM ImmuneTo WHERE CreatureName = '" + creatureName + "' "
        resits = "SELECT DamageName FROM Resists WHERE CreatureName = '" + creatureName + "' "
        weak = "SELECT DamageName FROM WeakTo WHERE CreatureName = '" + creatureName + "' "

        queryStr = "SELECT Spell.SpellName, SpellDmgType.DamageName, CASE "
        queryStr += " WHEN SpellDmgType.DamageName in ( " + weak + ") THEN MaxDamage*2 "
        queryStr += " WHEN SpellDmgType.DamageName in ( " + resits + ") THEN MaxDamage/2 "
        queryStr += " WHEN SpellDmgType.DamageName in ( " + immune + ") THEN 0 "
        queryStr += " ELSE MaxDamage "
        queryStr += "END AS Maximum FROM Spell LEFT JOIN SpellDmgType on Spell.SpellName = SpellDmgType.SpellName " 
        queryStr += "WHERE Level = " + level + " AND Maximum != 0 Order By Maximum DESC limit 1"
        result = self.cur.execute(queryStr)
        result = self.convert(result)
        return result
    def getPreWrittenQuery13(self):
        queryStr = "SELECT Alignment, OC.CreatureName, Language FROM Creature OC "
        queryStr += "JOIN Languages on OC.CreatureName = Languages.CreatureName "
        queryStr += "WHERE Language = 'Any' or Language = 'All' or Language in ( "
        queryStr += "SELECT Language FROM Creature IC "
        queryStr += "JOIN Languages ON IC.CreatureName = Languages.CreatureName "
        queryStr += "Group By Language HAVING Language != 'None' AND IC.Alignment = OC.Alignment AND Language != 'Any' AND Language != 'All' "
        queryStr += "ORDER BY count(DISTINCT IC.CreatureName) DESC limit 1 ) "
        queryStr += "ORDER BY Alignment "
        result = self.cur.execute(queryStr)
        result = self.convert(result)
        return result
    def getPreWrittenQuery14(self):
        queryStr = "SELECT Feature, count(DISTINCT Creature.CreatureName) AS NumCreatureHas FROM Creature "
        queryStr += "JOIN AdditionalFeature ON Creature.CreatureName = AdditionalFeature.CreatureName "
        queryStr += "Group By Feature Order By NumCreatureHas DESC "
        result = self.cur.execute(queryStr)
        result = self.convert(result)
        return result
# def main():
#     db = MyDatabase()


# if __name__ == "__main__":
#     main()