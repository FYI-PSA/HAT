package com.example.versteckt.modules;

import java.math.BigInteger;

class big
{

    public static final BigInteger n(Character value)
    {
        return new BigInteger(value.toString());
    }
    public static final BigInteger n(Long value)
    {
        return new BigInteger(value.toString());
    }
    public static final BigInteger n(Integer value)
    {
        return new BigInteger(value.toString());
    }
    public static final BigInteger n(String value)
    {
        return new BigInteger(value);
    }
    

    public static final BigInteger zero = big.n("0");
    public static final BigInteger one = big.n("1");
    public static final BigInteger two = big.n("2");
    public static final BigInteger mOne = big.n("-1"); 

    
    public static final BigInteger add(BigInteger A, BigInteger B)
    {
        return A.add(B);
    }
    public static final BigInteger sub(BigInteger minuend, BigInteger subtrahend)
    {
        return minuend.subtract(subtrahend);
    }
    
    
    public static final BigInteger mul(BigInteger A, BigInteger B)
    {
        return A.multiply(B);
    }
    public static final BigInteger div(BigInteger dividend, BigInteger divisor)
    {
        return dividend.divide(divisor);
    }
    public static final BigInteger pow(BigInteger base, Integer exponent)
    {
        return base.pow(exponent);
    }
    public static final BigInteger mod(BigInteger A, BigInteger B)
    {
        return A.mod(B);
    }


    public static final BigInteger modpow(BigInteger base, BigInteger exponent, BigInteger mod)
    {
        return base.modPow(exponent, mod);
    }
    public static final BigInteger gcd(BigInteger A, BigInteger B)
    {
        return A.gcd(B);
    }
    public static final BigInteger mmi(BigInteger A, BigInteger B)
    {
        return A.modInverse(B);
    }


    public static final Boolean less(BigInteger A, BigInteger B)
    {
        if ((A.compareTo(B)) < 0)
        {
            return true;
        }
        return false;
    }
    public static final Boolean more(BigInteger A, BigInteger B)
    {
        if ((A.compareTo(B)) > 0)
        {
            return true;
        }
        return false;
    }
    public static final Boolean equal(BigInteger A, BigInteger B)
    {
        if ((A.compareTo(B)) == 0)
        {
            return true;
        }
        return false;
    }


    public static final Boolean eqless(BigInteger A, BigInteger B)
    {
        if (equal(A, B) || less(A, B))
        {
            return true;
        }
        return false;
    }
    public static final Boolean eqmore(BigInteger A, BigInteger B)
    {
        if (equal(A, B) || more(A, B))
        {
            return true;
        }
        return false;
    }
}