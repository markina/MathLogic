---mySum
---mySub
---myDiv
---myMod
---isPrime
---myNthPrime

z x = 0

n x = (head x) + 1

uFirst ls = head ls

uSecond ls = head (tail ls)

uThird ls = head (tail (tail ls))

getLsGX lsG lsX
    | length lsG == 1 = ((head lsG) lsX) : []
    | otherwise = ((head lsG) lsX) : (getLsGX (tail lsG) lsX) 
-- *Main> getLsGX [sum,sum] [1,2]
-- [3,3]


s f lsG lsX = f (getLsGX lsG lsX)
-- *Main> s sum [uFirst, uSecond] [1,2,3] 
-- 3

r f g lsX y
    | y < 0 = undefined
    | y == 0 = f lsX
    | otherwise = g lsX (y-1) (r f g lsX (y-1))

gsum x y z = s n (uThird:[]) ((head x):y:z:[])

mySum a b = r uFirst gsum (a:[]) b  
-- *Main> mySum 10 4
-- 14

mySumLs ls = r uFirst gsum ((head ls):[]) (uSecond ls)

gmult x y z = s mySumLs (uFirst:uThird:[]) ((head x):y:z:[]) 
-- *Main> gmult 1 2 3
-- 4

myMult a b = r z gmult (a:[]) b
-- *Main> myMult 8 9
-- 72

myMultLs ls = r z gmult ((head ls):[]) (head (tail ls))

gdec x y z = uSecond ((head x):y:z:[])

myDec a = r z gdec (a:[]) a
-- *Main> myDec 2
-- 1

myDecLs ls = r z gdec ((head ls):[]) (head ls)
-- *Main> myDecLs [2]
-- 1
-- *Main> myDecLs [2,9]
-- 1


gsub x y z = s myDecLs (uThird:[]) ((head x):y:z:[])

mySub a b = r uFirst gsub (a:[]) b
-- *Main> mySub 20 13
-- 7

hpow ls = s n (z:[]) ls
gpow x y z = s myMultLs (uFirst:uThird:[]) ((head x):y:z:[])

myPow a b = r hpow gpow (a:[]) b 
-- *Main> myPow 2 10
-- 1024

hif ls = uSecond ls
gif ls c d = uFirst ((uFirst ls):(uSecond ls):c:d:[])

myIf x y z = r hif gif (y:z:[]) x
-- *Main> myIf 0 3 4
-- 4
-- *Main> myIf 1 3 4
-- 3

hEqZ ls = 1
gEqZ ls b c = s z (uSecond:[]) (b:c:[])

myEqZ a = r hEqZ gEqZ (a:[]) a
-- *Main> myEqZ 3
-- 0
-- *Main> myEqZ 0
-- 1

myLe a b = myEqZ (mySub a b)
-- *Main> myLe 1 2
-- 1
-- *Main> myLe 1 1
-- 1
-- *Main> myLe 2 1
-- 0

myEq a b = myMult (myLe a b) (myLe b a) 
-- *Main> myEq 2 3
-- 0
-- *Main> myEq 2 2
-- 1

myLower a b = myMult (myLe a b) (myLe (n (a:[])) b)
-- *Main> myLower 2 3
-- 1
-- *Main> myLower 2 2
-- 0

na a = n (a:[])
gDivMax lsb pa pr  = myIf (myEq (mySub (na pa)  (pr) ) (head lsb) ) (na pa) (pr) 
-- *Main> gDivMax [2] 1 5 
-- 5
-- *Main> gDivMax [2] 1 2 
-- 3
-- *Main> gDivMax [2] 3 0 
-- 3

hDivMax ls = 0

myDivMax a 0 = undefined
myDivMax a b = r hDivMax gDivMax (b:[]) a

-- *Main> myDivMax 1 2
-- 0
-- *Main> myDivMax 2 2
-- 2
-- *Main> myDivMax 3 2
-- 2
-- *Main> myDivMax 4 2
-- 4
-- *Main> myDivMax 5 2
-- 4


{-                y
    | 0 | 1 | 2 | 3 | 4 | 5 | 6 |
  0 | - | 0 | 0 | 0 | 0 | 0 | 0 |
  1 | - | 1 | 0 | 0 | 0 | 0 | 0 |
x 2 | - | 2 | 2 | 0 | 0 | 0 | 0 | 
  3 | - | 3 | 2 | 3 | 0 | 0 | 0 |
  4 | - | 4 | 4 | 3 | 4 | 0 | 0 |
  5 | - | 5 | 4 | 3 | 4 | 5 | 0 |
-}

gDiv lsb pa pr  = mySum (pr) (myEq (n (pa:[]) ) (myDivMax (n (pa:[]) ) (head lsb) ) )
         
hDiv ls = 0
myDiv a 0 = undefined
myDiv a b = r hDiv gDiv (b:[]) a
-- *Main> myDiv 0 0
-- *** Exception: Prelude.undefined
-- *Main> myDiv 30 10
-- 3
-- *Main> myDiv 30 5
-- 6
-- *Main> myDiv 30 6
-- 5
-- *Main> myDiv 30 7
-- 4

myMod a b = mySub (a) (myMult b (myDiv a b) )


isTwo a = myEq (n ((n (0:[])):[])) a
-- *Main> isTwo 8
-- 0
-- *Main> isTwo 2
-- 1

hIsPrime ls = 0
gIsPrime lsa pa pr = mySum (myEqZ( myMod (head lsa) (n (pa:[])) )) (pr)
isPrime a = isTwo ( r hIsPrime gIsPrime (a:[]) a ) 
-- *Main> isPrime 8
-- 0
-- *Main> isPrime 39
-- 0
-- *Main> isPrime 23
-- 1
-- *Main> isPrime 19
-- 1
-- *Main> isPrime 20
-- 0

myMax a b = myIf (myLe a b) b a 
-- *Main> myMax 2 3
-- 3
-- *Main> myMax 3 2
-- 3
-- *Main> myMax 2 2
-- 2

myMin a b = myIf (myLe a b) a b

hNextPrime ls = myIf (myEqZ (head ls)) ((n ((n (0:[])):[]))) (mySum (head ls) (head ls))

gNextPrime lsa pa pr = myMin ( myIf (isPrime (w)) (w) (hNextPrime lsa) ) (pr)
   where w = (head lsa) + (na pa) 
myNextPrime a = r hNextPrime gNextPrime (a:[]) a
-- *Main> myNextPrime 0
-- 2
-- *Main> myNextPrime 6
-- 7
-- *Main> myNextPrime 7
-- 11
-- *Main> myNextPrime 12
-- 13
-- *Main> myNextPrime 13
-- 17

gNthPrime lsa pa pr = myNextPrime pr
hNthPrime ls = 0
myNthPrime a = r hNthPrime gNthPrime (a:[]) a 
-- *Main> myNthPrime 1
-- 2
-- *Main> myNthPrime 2
-- 3
-- *Main> myNthPrime 6
-- 13
-- *Main> myNthPrime 8
-- 19
-- *Main> myNthPrime 10
-- 29

hPlog lsab = 0
gPlog lsab pa pr = myMax ( myIf (myEqZ (myMod (uSecond lsab) (myPow (head lsab) (na pa) ) )) (na pa) (0) ) (pr)
myPlog 1 b = undefined
myPlog a b = r hPlog gPlog (a:b:[]) (myDiv b a)
-- *Main> myPlog 3 12 
-- 1
-- *Main> myPlog 2 8
-- 3
-- *Main> myPlog 2 16
-- 4
-- *Main> myPlog 5 7 
-- 0


