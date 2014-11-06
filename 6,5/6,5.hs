{-# OPTIONS_GHC -XStandaloneDeriving #-}
module HW where

---- getAkk

z x = 0

n x = (head x) + 1

uFirst ls = head ls

uSecond ls = head (tail ls)

uThird ls = head (tail (tail ls))

getLsGX lsG lsX
    | length lsG == 1 = ((head lsG) lsX) : []
    | otherwise = ((head lsG) lsX) : (getLsGX (tail lsG) lsX) 

s f lsG lsX = f (getLsGX lsG lsX)

r f g lsX y
    | y < 0 = undefined
    | y == 0 = f lsX
    | otherwise = g lsX (y-1) (r f g lsX (y-1))

mySum a b = a + b  
mySumLs ls = (head ls) + (head (tail ls))

myMult a b = a * b
myMultLs ls = (head ls) * (head (tail ls))

myDec a = a + 1

myDecLs ls = (head ls) + 1 

mySub a b
	| a < b = 0
	| otherwise = a - b
mySubLs ls
	| (head ls) < (head (tail ls)) = 0
	| otherwise = (head ls) - (head (tail ls))

myPow a b = a ^ b
 
myIf x y z
	| x == 1 = y
	| otherwise = z
myIfLs (x:y:z:[]) = myIf x y z
myIfN x y z 
    | x = y
    | otherwise = z
    
	

myEqZ a
	| a == 0 = 1
	| otherwise = 0
myEqZLs (a:[]) = myEqZ a

myLe a b
	| a <= b = 1
	| otherwise = 0
myLeLs (a:b:[]) = myLe a b

myEq a b
	| a == b = 1
	| otherwise = 0

myLower a b
	| a < b = 1
	| otherwise = 0

na a = n (a:[])

myDiv a 0 = undefined
myDiv a b = a `div` b
myDivLs (a:0:[]) = undefined 
myDivLs (a:b:[]) = a `div` b

myModWithS a b = s mySubLs
                 (uFirst:uSecond:[])
                 (a:( s myMultLs
                      (uFirst:uSecond:[])
                      (b:( s myDivLs
                           (uFirst:uSecond:[])
                           (a:b:[])
                         ):[]) 
                    ):[])
myModLs (a:b:[]) = myModWithS a b
                    
isTwo a
	| a == 2 = 1
	| otherwise = 0 
isTwoLs (a:[])
	| a == 2 = 1
	| otherwise = 0 

hIsPrimeWithS ls = 0
gIsPrimeWithS lsa pa pr = s mySumLs 
                          (uFirst:uSecond:[])
                          (
                              (
                                  s myEqZLs 
                                  (uFirst:[])
                                  (
                                      (
                                          s myModLs 
                                          (uFirst:uSecond:[])
                                          (
                                                (head lsa):
                                                (n (pa:[])):
                                                []
                                          )
                                      ):
                                      []
                                  )
                              ):
                              (pr):
                              []
                          )
                          
isPrimeWithS a = s isTwoLs 
                    (uFirst:[])
                    (
                        ( r hIsPrimeWithS gIsPrimeWithS (a:[]) a ) :
                        []
                    )

myMaxWithS a b = s myIfLs 
                    (uFirst: uSecond: uThird : [])                    
                    (
                        (
                            s myLeLs 
                                (uFirst: uSecond :[])
                                (
                                    a :
                                    b :
                                    []
                                )
                        
                        ):
                        
                        b :
                        a :
                        []
                       
                    ) 

myMinWithS a b = s myIfLs 
                    (uFirst: uSecond: uThird : [])                    
                    (
                        (
                            s myLeLs 
                                (uFirst: uSecond :[])
                                (
                                    a :
                                    b :
                                    []
                                )
                        
                        ):
                        
                        a :
                        b :
                        []
                       
                    )
myMinLs (a:b:[]) = myMinWithS a b

hNextPrimeWithS ls = s myIfLs 
                        (uFirst: uSecond: uThird : [])                        
                        (
                            (s myEqZLs 
                                (uFirst:[])
                                (
                                    (head ls):
                                    []
                                )
                            ): 
                            ((n ((n (0:[])):[]))): 
                            (s mySumLs 
                                (uFirst: uSecond:[])
                                (
                                    (head ls) :
                                    (head ls) :
                                    []
                                )    
                            
                            ):
                            []
                        )

gNextPrimeWithS lsa pa pr = s myMinLs 
                                (uFirst: uSecond:[])                                
                                (
                                    (s myIfLs 
                                        (uFirst: uSecond:uThird:[])
                                        (
                                            (isPrimeWithS ((head lsa) + (na pa))) :
                                            ((head lsa) + (na pa)) :
                                            (hNextPrimeWithS lsa) :
                                            []
                                        )                                    
                                    ) :
                                    (pr):
                                    []
                                )

myNextPrimeWithS a = r hNextPrimeWithS gNextPrimeWithS (a:[]) a
myNextPrimeLs (a:[]) = myNextPrimeWithS a

gNthPrimeWithS lsa pa pr = s myNextPrimeLs 
                            (uFirst:[])
                            (pr:[])
hNthPrimeWithS ls = 0

myNthPrimeWithS a = r hNthPrimeWithS gNthPrimeWithS (a:[]) a 
myNthPrimeLs (a:[]) = myNthPrimeWithS a 

fHelper f lsx y 
        | f lsx y == 0 = y
        | otherwise = fHelper f lsx (y+1)  

m f lsx = fHelper f lsx 0 

fSize lsx y 
        | (head lsx) == 1 = 0
        | (head lsx) `rem` (myNthPrimeWithS (y+1) ) /= 0 = 0
        | otherwise = 1  

mySizeWithS l = m fSize (l:[]) 
mySizeLs (l:[]) = mySizeWithS l

myPushWithS l a = l * (
                    (
                        s myNthPrimeLs 
                        ((uFirst):[])
                        (((
                            s mySizeLs
                                (uFirst:[])
                                (l:[])
                          ) +1):[])
                    ) ^ (a + 1))
myPushLs (l:a:[]) = myPushWithS l a 

fPopWithS ls y
        | y == 0 = 1
        | (head ls) `rem` ((head (tail ls) )^(y+1)) /= 0 = 0
        | otherwise = 1


myLastWithS l = s myNthPrimeLs 
                    (uFirst:[])                
                    ((
                        s mySizeLs 
                            (uFirst:[])
                            (l:[])
                     ):[])
myLastLs (l:[]) = myLastWithS l 
myPopWithS l =  l `div` ((
                    s myLastLs 
                    (uFirst:[])
                    (l:[])
                    
                    ) ^ (m fPopWithS (l:(
                                    s myLastLs 
                                    (uFirst:[])
                                    (l:[])
                                    
                                    ):[]) ))
myPopLs (l:[]) = myPopWithS l 

myTopWithS l = (m fPopWithS (l:(
                            s myLastLs 
                            (uFirst:[])
                            (l:[])
                            ):[]) ) - 1
myTopLs (l:[]) = myTopWithS l  

getStackBeginWithS a b = (2 ^ (a + 1)) * (3 ^ (b + 1))
getStackBeginLs (a:b:[]) = getStackBeginWithS a b  

getSecondWithS l = s myTopLs 
                        (uFirst:[])                        
                        (l:[])
getSecondLs (l:[]) = getSecondWithS l 
 
getFirstWithS l = s myTopLs 
                    (uFirst:[])
                    ((
                        s myPopLs 
                        (uFirst:[])
                        (l:[])
                      ):[])
getFirstLs (l:[]) = getFirstWithS l  

myMinusWithS l = s myPopLs 
                    (uFirst:[])                    
                    ((
                        s myPopLs 
                        (uFirst:[])
                        (l:[])
                    ):[])
myMinusLs (l:[]) = myMinusWithS l                     

minusTwoAddTwoWithS l a b = s myPushLs 
                                (uFirst:uSecond:[])  
                                (                              
                                    (
                                        s myPushLs 
                                        (uFirst:uSecond:[]) 
                                        (
                                            (
                                                s myMinusLs
                                                (uFirst:[])
                                                (l:[])
                                            ): 
                                            (a-1):
                                            []
                                        )
                                    ) :
                                    b :
                                    []
                                )
                                
minusTwoAddTwoLs (l:a:b:[]) = minusTwoAddTwoWithS l a b                                  

minusTwoAddThreeWithS l a b = s myPushLs 
                                    (uFirst:uSecond:[])
                                    (
                                        (
                                            s myPushLs
                                                (uFirst:uSecond:[]) 
                                                (
                                                    (
                                                        s myPushLs 
                                                            (uFirst:uSecond:[])
                                                            (
                                                                (
                                                                    s myMinusLs
                                                                        (uFirst:[])
                                                                        (l:[])
                                                                ) :
                                                                (a-1) :
                                                                []
                                                            )
                                                    ) :
                                                    (a):
                                                    []
                                                )
                                            
                                            ) :
                                        (b-1) :
                                        []
                                    )
minusTwoAddThreeLs (l:a:b:[]) = minusTwoAddThreeWithS l a b  

hGetStackAfterYWithS ls = head ls

gGetStackAfterYWithS lsl py pr
        | (
                s getFirstLs 
                    (uFirst:[])    
                    (pr:[])
          ) == 0 = 
                    s myPushLs 
                        (uFirst:uSecond:[])
                        (
                            (
                                s myMinusLs 
                                (uFirst:[])
                                (pr:[])
                            ) :
                            (
                                (
                                    s getSecondLs 
                                    (uFirst:[])
                                    (pr:[])
                                ) + 1) :
                            []
                        )
        | ( (
                s getFirstLs 
                    (uFirst:[])
                    (pr:[])
            ) > 0 ) && ((
                            s getSecondLs 
                                (uFirst:[])
                                (pr:[])
                        ) == 0) = (
                                        s minusTwoAddTwoLs 
                                            (uFirst:uSecond:uThird:[])
                                            (
                                                (pr) :
                                                (
                                                    s getFirstLs 
                                                        (uFirst:[])
                                                        (pr:[])
                                                ) :
                                                (1) :
                                                []
                                            )
                                  )
        | ( (
                s getFirstLs 
                    (uFirst:[])
                    (pr:[])
                ) > 0 ) && ((
                                s getSecondLs
                                    (uFirst:[]) 
                                    (pr:[])
                             ) > 0) = (
                                            s minusTwoAddThreeLs 
                                                (uFirst:uSecond:uThird:[])
                                                (
                                                    (pr) : 
                                                    (
                                                        s getFirstLs 
                                                            (uFirst:[])
                                                            (pr:[])
                                                    ) :
                                                    (
                                                        s getSecondLs 
                                                            (uFirst:[])
                                                            (pr:[])
                                                    ) :
                                                    []
                                                )
                                      )


getStackAfterYWithS l y = r hGetStackAfterYWithS gGetStackAfterYWithS (l:[]) y 
getStackAfterYLs (l:y:[]) = getStackAfterYWithS l y 
       
fGetCntStepWithS ls y
        | y == 0 = 1
        | s mySizeLs 
                (uFirst:[])
                ((
                      s getStackAfterYLs 
                          (uFirst:uSecond:[])
                          (
                                (head ls)  :
                                (y) : 
                                []
                          )
                 ):[])
          == 1 = 0
        | otherwise = 1

getCntStepHelperWithS l = m fGetCntStepWithS (l:[]) 
getCntStepHelperLs (l:[]) = getCntStepHelperWithS l   

getCntStepWithS a b = s getCntStepHelperLs 
                            (uFirst:[])                            
                            ((
                                   s getStackBeginLs 
                                        (uFirst:uSecond:[])
                                        (
                                            a:b:[]
                                        )
                             ):[])
getCntStepLs (a:b:[]) = getCntStepWithS a b                               

getAkkWithS a b = s myTopLs 
                        (uFirst:[])                        
                        (
                              (
                               s getStackAfterYLs 
                                 (uFirst:uSecond:[])
                                 (
                                      (
                                           s getStackBeginLs
                                                (uFirst:uSecond:[])
                                                (
                                                    a : b :[]
                                                )
                                      ) :  
                                      (
                                            s getCntStepLs
                                                (uFirst:uSecond:[])
                                                (
                                                 a:b:[]
                                                )
                                      ) :[] 
                                  )
                                 ) : []
                        )
{-
Prelude HW> getAkkWithS 1 1
3
Prelude HW> getAkkWithS  0 0
1
Prelude HW> getAkkWithS  0 2
3
Prelude HW> getAkkWithS  0 3
4
Prelude HW> getAkkWithS  0 6
7
Prelude HW> getAkkWithS  1 2
4
Prelude HW> getAkkWithS  2 2
7
Prelude HW> getAkkWithS  2 3
9
Prelude HW> getAkkWithS  2 1
5
-}
