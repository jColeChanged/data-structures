;; In my last post I spent a lot of time talking about the recursive squaring
;; algorithm. Toward the end I ended up showing that you could replace a
;; function within this function with the expectation that the code will still
;; work.
;;
;; In case you've forgotten the bit that could be switched out was the `*`
;; function. The code I'm talking about is below.:

(defn power [a b]
  "Calculates a^b when b is a natural number.

  Args:
    a: A number to be raised to power b.
    b: A number to raise a by.

  Returns:
    This algorithm returns the result of raising a ^ b."
  (cond
   (= b 0) 1
   (= b 1) a
   (= b 2) (* a a)
   (even? b) (power (power a (/ b 2)) 2)
   (odd? b) (* (power a (- b 1)) a)))

;; One of the things that many programmers love is this idea of abstraction.
;; Some languages let you work at higher levels than others. For example, some
;; languages let you treat functions as arguments.
;;
;; So lets see if we can make this code a bit more general through the use of
;; something commonly reffered to as higher-order functions.

(defn higher-order-power [f a b]
  "A higher-order function which calculates a^b when b is a natural number.

  Args:
    a: A number to be raised to power b.
    b: A number to raise a by.
    f: The function to use when multiplying a by b.

  Returns:
    This algorithm returns the result of raising a ^ b."
  (cond
   (= b 0) 1
   (= b 1) a
   (= b 2) (f a a)
   (even? b) (higher-order-power f (higher-order-power f a (/ b 2)) 2)
   (odd? b) (f (higher-order-power f a (- b 1)) a)))

(defn create-power-function [f]
  "This function returns a function which returns the results of raising a^b.

  Args:
    f: The function to use when multiplying a by b.

  Returns:
    This function returns an algorithm which returns the result of raising
    a^b."
  (fn pow [a b]
    (cond
     (= b 0) 1
     (= b 1) a
     (= b 2) (f a a)
     (even? b) (pow (pow a (/ b 2)) 2)
     (odd? b) (f (pow a (dec b)) a))))