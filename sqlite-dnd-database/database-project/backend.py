#!/usr/bin/python3


import socket
import json
import sys
import traceback
import re

from myDatabase import MyDatabase

serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

hostname = '127.0.0.1'
portNum = 9999

serversocket.bind((hostname, portNum))

print(hostname)


myData = None

db = MyDatabase()


def convertResult(result):
    retStr = 'Content-Type: application/json\r\n\r\n'
    retStr += json.dumps(result)
    return retStr


while True:
    serversocket.listen(5)

    try:
        conn, addr = serversocket.accept()
        # multi thread here!
        with conn:  # this is a socket! With syntax does not work on python 2

            try:

                data = conn.recv(1024)
                # Sometimes it sends some empty message
                if data != None and data != "" and len(data) != 0:
                    data = data.decode()
                    data = data.splitlines()
                    print(data[0])
                    request = data[0].split(" ")

                    header = 'HTTP/1.1 200 OK\r\n'
                    if request[0] == 'GET':

                        # Main table is requested
                        if request[1] == '/':
                            page = open('frontend.html').read()
                            header += 'Content-Type: text/html\r\n\r\n'
                            header += page
                        elif request[1] == '/getCreature':
                            result = db.getCreature()
                            header += convertResult(result)
                        elif request[1] == '/getWeapon':
                            result = db.getWeapon()
                            header += convertResult(result)
                        elif request[1] == '/getSpell':
                            result = db.getSpell()
                            header += convertResult(result)
                        elif request[1] == '/getPlayerClass':
                            result = db.getPlayerClass()
                            header += convertResult(result)
                        elif request[1] == '/getDamageType':
                            result = db.getDamageType()
                            header += convertResult(result)
                        
                        # Support table is requested
                        elif request[1] == '/getAdditionalFeature':
                            result = db.getAdditionalFeats()
                            header += convertResult(result)
                        elif request[1] == '/getCanUseSpell':
                            result = db.getCanUseSpell()
                            header += convertResult(result)
                        elif request[1] == '/getCanUseWeapon':
                            result = db.getCanUseWeapon()
                            header += convertResult(result)
                        elif request[1] == '/getClassSTs':
                            result = db.getClassSTs()
                            header += convertResult(result)
                        elif request[1] == '/getCreatureSTs':
                            result = db.getCreatureSTs()
                            header += convertResult(result)
                        elif request[1] == '/getImmuneTo':
                            result = db.getImmuneTo()
                            header += convertResult(result)
                        elif request[1] == '/getLanguages':
                            result = db.getLanguages()
                            header += convertResult(result)
                        elif request[1] == '/getResists':
                            result = db.getResists()
                            header += convertResult(result)
                        elif request[1] == '/getSenses':
                            result = db.getSenses()
                            header += convertResult(result)
                        elif request[1] == '/getSkills':
                            result = db.getSkills()
                            header += convertResult(result)
                        elif request[1] == '/getSpeeds':
                            result = db.getSpeeds()
                            header += convertResult(result)
                        elif request[1] == '/getSpellDmgType':
                            result = db.getSpellDmgType()
                            header += convertResult(result)
                        elif request[1] == '/getWeakTo':
                            result = db.getWeakTo()
                            header += convertResult(result)
                        elif request[1] == '/getWeaponDmgType':
                            result = db.getWeapDmgType()
                            header += convertResult(result)

                        # Checkboxes were ticked or column sorting is needed
                        elif re.match("^/manipulateTable/", request[1]) is not None:
                            getName = request[1].split('/')
                            key = getName[-1]
                            keys = key.split('_')
                            result = db.manipulateTable(keys)
                            header += convertResult(result)
                        elif re.match("^/getSortedTable/", request[1]) is not None:
                            getName = request[1].split('/')
                            key = getName[-1]
                            keys = key.split('_')
                            result = db.getSortedTable(keys)
                            header += convertResult(result)

                        # Multivalued Attribute boxes are pressed
                        elif re.match("^/getWeapInfo/", request[1]) is not None:
                            getName = request[1].split('/')
                            weapName = getName[-1]
                            result = db.getWeaponInfo(weapName)
                            header += convertResult(result)
                        elif re.match("^/getPlayerInfoWeap/", request[1]) is not None:
                            getName = request[1].split('/')
                            className = getName[-1]
                            result = db.getPlayerInfoWeap(className)
                            header += convertResult(result)
                        elif re.match("^/getPlayerInfoSpell/", request[1]) is not None:
                            getName = request[1].split('/')
                            className = getName[-1]
                            result = db.getPlayerInfoSpell(className)
                            header += convertResult(result)
                        elif re.match("^/getPlayerSTInfo/", request[1]) is not None:
                            getName = request[1].split('/')
                            className = getName[-1]
                            result = db.getPlayerSTInfo(className)
                            header += convertResult(result)
                        elif re.match("^/getCreatureSTInfo/", request[1]) is not None:
                            getName = request[1].split('/')
                            className = getName[-1]
                            result = db.getCreatureSTInfo(className)
                            header += convertResult(result)
                        elif re.match("^/getSpellInfo/", request[1]) is not None:
                            getName = request[1].split('/')
                            spellName = getName[-1]
                            result = db.getSpellInfo(spellName)
                            header += convertResult(result)
                        elif re.match("^/getCreatureSkillInfo/", request[1]) is not None:
                            getName = request[1].split('/')
                            className = getName[-1]
                            result = db.getCreatureSkillInfo(className)
                            header += convertResult(result)
                        elif re.match("^/getCreatureImmuneInfo/", request[1]) is not None:
                            getName = request[1].split('/')
                            className = getName[-1]
                            result = db.getCreatureImmuneInfo(className)
                            header += convertResult(result)
                        elif re.match("^/getCreatureWeakInfo/", request[1]) is not None:
                            getName = request[1].split('/')
                            className = getName[-1]
                            result = db.getCreatureWeakInfo(className)
                            header += convertResult(result)
                        elif re.match("^/getCreatureResistInfo/", request[1]) is not None:
                            getName = request[1].split('/')
                            className = getName[-1]
                            result = db.getCreatureResistInfo(className)
                            header += convertResult(result)
                        elif re.match("^/getCreatureLanguageInfo/", request[1]) is not None:
                            getName = request[1].split('/')
                            className = getName[-1]
                            result = db.getCreatureLanguageInfo(className)
                            header += convertResult(result)
                        elif re.match("^/getCreatureSenseInfo/", request[1]) is not None:
                            getName = request[1].split('/')
                            className = getName[-1]
                            result = db.getCreatureSenseInfo(className)
                            header += convertResult(result)
                        elif re.match("^/getCreatureSpeedInfo/", request[1]) is not None:
                            getName = request[1].split('/')
                            className = getName[-1]
                            result = db.getCreatureSpeedInfo(className)
                            header += convertResult(result)
                        elif re.match("^/getCreatureAddFeature/", request[1]) is not None:
                            getName = request[1].split('/')
                            className = getName[-1]
                            result = db.getCreatureAddFeature(className)
                            header += convertResult(result)

                        elif request[1] == '/getPreWrittenQuery1':
                            result = db.getPreWrittenQuery1()
                            header += convertResult(result)
                        elif request[1] == '/getPreWrittenQuery2':
                            result = db.getPreWrittenQuery2()
                            header += convertResult(result)
                        elif request[1] == '/getPreWrittenQuery3':
                            result = db.getPreWrittenQuery3()
                            header += convertResult(result)
                        elif request[1] == '/getPreWrittenQuery4':
                            result = db.getPreWrittenQuery4()
                            header += convertResult(result)
                        elif request[1] == '/getPreWrittenQuery5':
                            result = db.getPreWrittenQuery5()
                            header += convertResult(result)
                        elif request[1] == '/getPreWrittenQuery6':
                            result = db.getPreWrittenQuery6()
                            header += convertResult(result)
                        elif request[1] == '/getPreWrittenQuery7':
                            result = db.getPreWrittenQuery7()
                            header += convertResult(result)
                        elif request[1] == '/getPreWrittenQuery8':
                            result = db.getPreWrittenQuery8()
                            header += convertResult(result)
                        elif request[1] == '/getPreWrittenQuery9':
                            result = db.getPreWrittenQuery9()
                            header += convertResult(result)
                        elif request[1] == '/getPreWrittenQuery10':
                            result = db.getPreWrittenQuery10()
                            header += convertResult(result)
                        elif request[1] == '/getPreWrittenQuery11':
                            result = db.getPreWrittenQuery11()
                            header += convertResult(result)
                        elif re.match("^/getPreWrittenQuery12/", request[1]) is not None:
                            getName = request[1].split('/')
                            keys = getName[-1]
                            keys = keys.split('_')
                            result = db.getPreWrittenQuery12(keys)
                            header += convertResult(result)
                        elif request[1] == '/getPreWrittenQuery13':
                            result = db.getPreWrittenQuery13()
                            header += convertResult(result)
                        elif request[1] == '/getPreWrittenQuery14':
                            result = db.getPreWrittenQuery14()
                            header += convertResult(result)

                    conn.sendall(header.encode('utf-8'))
                else:
                    # print("??") empty message recevied?
                    pass
            except Exception as e:
                print(e)
                print(traceback.format_exc())
                db.close()
                sys.exit(0)
    except KeyboardInterrupt:
        print("KeyboardInterrupt")
        db.close()
        sys.exit(0)
