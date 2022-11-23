package com.group3.movieguide.Business;

import com.google.android.material.chip.Chip;
import com.group3.movieguide.R;

import java.util.ArrayList;

public class QuizChips
{
    public static String getChipName(Chip chip)
    {
        String res = null;

        if(chip.getId() == R.id.adventureChip)
            res  ="Action";
        else if(chip.getId() == R.id.dramaChip)
            res  ="Drama";
        else if(chip.getId() == R.id.fantasyChip)
            res  ="Fantasy";
        else if(chip.getId() == R.id.horrorChip)
            res  ="Horror";
        else if(chip.getId() == R.id.humorChip)
            res  ="Humor";
        else if(chip.getId() == R.id.mysteryChip)
            res  ="Mystery";
        else if(chip.getId() == R.id.nonFictionChip)
            res  ="Non fiction";
        else if(chip.getId() == R.id.romanceChip)
            res  ="Romance";
        else if(chip.getId() == R.id.scfiChip)
            res  ="Sci-fi";

        return res;
    }

    public static String getRatingChipName(Chip chip)
    {
        String res = null;

        if(chip.getId() == R.id.gRatedChip)
            res = "G";
        else if(chip.getId() == R.id.pgRatedChip)
            res = "PG";
        else if(chip.getId() == R.id.pg13RatedChip)
            res = "PG-13";
        else if(chip.getId() == R.id.rRatedChip)
            res = "R";
        else if(chip.getId() == R.id.nc17RatedChip)
            res = "NC-17";

        return res;
    }

    public static void addChipsToList(String name, ArrayList<String> al)
    {
        if(!al.contains(name))
            al.add(name);
    }


    public static void removeChipsFormList(String name, ArrayList<String> al)
    {
        if(al.contains(name))
            al.remove(name);
    }


}
