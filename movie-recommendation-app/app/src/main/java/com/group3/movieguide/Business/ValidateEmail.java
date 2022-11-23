package com.group3.movieguide.Business;

public class ValidateEmail
{
    public static boolean validate(String s)
    {
        boolean res = true;

        String first = "";
        String second = "";

        int i = 0;
        while(i < s.length() &&  s.charAt(i) != '@')
        {
            first += (s.charAt(i) + "");
            i++;
        }

        i++;
        if(i >= s.length() )
            res = false;

        // Should have something before the .
        if(res && s.charAt(i) == '.')
            res = false;
        while(res && i < s.length())
        {
            second += (s.charAt(i) + "");
            i++;
        }
        if(res)
            res =  validateFirst(first) && validateSecond(second);
        return res;
    }

    private static boolean validateFirst(String s)
    {
        boolean res = true;
        if(s == null || s.length() == 0)
            res = false;
        for(int i = 0; i < s.length() && res; i++)
        {
            if((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')
                    || (s.charAt(i) >= '0' && s.charAt(i) <= '9') || (s.charAt(i) == '.') )
                continue;
            else
                return res = false;
        }
        return res;
    }

    private static boolean validateSecond(String s)
    {
        boolean res = true;
        int i = 0;
        while(i < s.length() && res)
        {
            if((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) == '.'))
            {
                if((s.charAt(i) == '.'))
                {
                    String temp = "";
                    int j = i + 1;
                    while(j < s.length())
                    {
                        temp += (s.charAt(j) + "");
                        j++;
                        i++;
                    }

                    if(temp.equals("ca") || temp.equals("com"))
                        continue;
                    else
                        res = false;
                }
            }
            else
                res = false;
            i++;
        }
        return res;
    }
}