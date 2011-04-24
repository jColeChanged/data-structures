;; This is an example of a divide and conquer algorithm. It splits the problem
;; in half, solves that problem, and then combines the solution.
;;
;; This method of raising by a power can be called "recursive squaring." Given
;; a theoretical architecture in which multiplication is constant time, it
;; operates in \Theta(lgn) time.

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

;; It turns out that given Phi ^ n / sqrt(5) rounded to the nearest integer
;; is the nth number in the fibonacci sequence. This means that through the use
;; of recursive squaring it is possible to provide a \Theta(lgn) method of
;; computing the nth fibonacci number.
;;
;; Sadly, this method is unreliable when using floating point arithmetic. The
;; error margins of the floating point end up leading to inaccurate answers.
;; So some people would call this a niave use of recursive squaring.
;;
;; Nevertheless, code which implements this algorithm has been placed below.

(use `clojure.contrib.math)
(def *square-five* (sqrt 5))
(def *phi* (/ (+ 1 *square-five*) 2))

(defn fib-n [n]
  "Calculates/Approximates the nth number in the fibonacci sequence.

  Args:
    n: The index of the number in the sequence to return.

  Returns:
    The nth number of the sequence."
  (round (/ (power *phi* n) *square-five*)))

;; As already said, the previous fib-n is imperfect, because it is susceptible
;; to the limitations of floating point numbers. Another method of calculating
;; the nth fibonacci number is desired. One way to do this is to realize that:
;;                          n
;; [ f_n+1 f_n  ]     [1  1]
;; |            |  =  |    |
;; [ f_n   f_n-1]     [1  0]
;;
;; Matrix multiplication with a matrix of this size is constant time. So if you
;; have a function which multiplies matrixes you can take advantage of the
;; previously mentioned recursive squaring method to calculate the nth number of
;; the fibonacci sequence in T(n) time.
;;
;; It might not make sense to you that recursive squaring can be applied in this
;; case. However, it can be. If your having trouble recognizing this intuitively
;; try to think about things from a more macro perspective. Don't look at the
;; steps in multiplying the matrix. Instead acknowledge that multiplying the 2x2
;; matrix can be done in constant time. Once you have that intuition you should
;; be able to see that raising a number to a power is analagous to raising a 2x2
;; matrix to a power.
;;
;; The reason I'm making a big deal of this is because when I was watching the
;; video I wasn't quite as clever as the people in the classroom. I got caught
;; up with figuring out how to do matrix multiplication and missed the point of
;; the excercise until I realized that both algorithms can be thought of as:

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
   (= b 2) (some-constant-time-function a a)
   (even? b) (power (power a (/ b 2)) 2)
   (odd? b) (some-constant-time-function (power a (- b 1)) a)))

 
(def *fib-is-base-matrix*
     [[1 1]
      [1 0]])
     
;; At this point I want to write some of the above code as higher-order
;; functions and create functions using Clojure. So lets go back to that
;; generalized function we were looking at earlier:

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
   (= b 2) (some-constant-time-function a a)
   (even? b) (power (power a (/ b 2)) 2)
   (odd? b) (some-constant-time-function (power a (- b 1)) a)))
 


;; It should be pretty clear that some-function-constant-function is a function
;; which can be switched out. You could, say, have code like this:

(defn power [f a b]
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
   (even? b) (power (power a (/ b 2)) 2)
   (odd? b) (f (power a (- b 1)) a)))

;; Another way to write this function is by surrounding it in a closure which
;; contains the function you want to use. With the higher-order function your
;; using one function as if it was other functions. With this approach your
;; actually creating new functions.

(defn create-power-fn [f]
  "Returns a function which calculates a^b when b is a natural number.

  Args:
    f: The function to use when multiplying a by b.

  Returns:
  "
  (cond
   (= b 0) 1
   (= b 1) a
   (= b 2) (f a a)
   (even? b) (power (power a (/ b 2)) 2)
   (odd? b) (f (power a (- b 1)) a)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
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

lein (defn power [f a b]
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
   (even? b) (power (power a (/ b 2)) 2)
   (odd? b) (f (power a (- b 1)) a)))