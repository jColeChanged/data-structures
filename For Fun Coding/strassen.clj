;; Joshua Cole Copyright 2011
;; Release under the MIT License

;; In the famous CLRS algorithms textbook there is a section which discusses
;; matrix operations. Herein are some of my attempts at translating some of
;; these matrix operations into Clojure.

(def test-matrix [[1 2]
		  [1 2]])
(def other-test-matrix [[1 2 3 4]
			[1 2 3 4]
			[1 2 3 4]
			[1 2 3 4]])

(defn C_yx
  "Solves C<sub>yx</sub> when multiplying two matrices.

  Args:
    m1: A matrix which can be multiplied by m2.
    m2: A matrix which can be multiplied by m1.
    y: The y position of the matrix to fill in.
    x: The x position of the matrix to fill in.
    num-cols: The number of cols in the matrix.
    num-rows: The number of rows in the matrix.

  Returns:
    The value of C<sub>yx</sub> after multiplying m1 by m2."
  [m1 m2 y x num-cols num-rows]
  (reduce +
	  (map *
	       (map #(nth (nth m1 y) %1) (range num-cols))
	       (map #(nth (nth m2 %1) x) (range num-rows)))))

(defn multiply
  "Returns the product of m1 and m2.

  Note that this algorithm is implemented in a naive manner. It is not written
  to be fast. It is also not written to be robust. Use at your own peril.

  Args:
    m1: A matrix which can be multpilied by m2.
    m2: A matrix which can be multiplied by m1.

  Returns:
    The product of the two matrices."
  [m1 m2]
  (let [num-rows (count m1) num-cols (count (first m1))]
    (vec (for [y (range num-rows)]
      (vec (for [x (range num-cols)]
	(C_yx m1 m2 y x num-rows num-cols)))))))

(defn split
  "Returns A split through the middle.

  Am I crazy in thinking that this has O(mlg(n)) running time?

  Args:
    A: A NxM matrix to split into fourths which has the property that
    both n and m are powers of two.

  Returns:
    The matrix split through the middle into four new matrices such that
    [[1 2] [3 4]] becomes ([1] [2] [3] [4]).

   In other words given a matrix it will return:
     (Top left, Top right, Bottom left, Bottom right)"
  [A]
  (let [num-rows (count A) ;; O(1)
	num-cols (count (A 0)) ;; count is constant time,
	                       ;; but the other is log_32(n)
	row-split (/ num-cols 2)  ;; O(1) Haven't checked this
	col-split (/ num-rows 2)] ;; O(1) Haven't checked this
    (list
     (vec (for [y (range col-split)]     ;; m/2 iterations
	    (subvec (A y) 0 row-split))) ;; O(1) for subvec but log_32n otherwis
     (vec (for [y (range col-split)]     ;; m/2 iterations
	    (subvec (A y) row-split)))   ;; same..
     (vec (for [y (range col-split num-rows)] ;; and so on and so forth 
	    (subvec (A y) 0 row-split))) ;; giving us 4 * m/2 * O(log_32n)
     (vec (for [y (range col-split num-rows)] ;; which is 2m * O(1)
	    (subvec (A y) row-split))))))

;; So all in all we have 3 * O(1) + log_32(n) + 2m * O(log_32n).
;; This reduces to O(mlogn), but the analysis is ignoring vec and vector calls
;; because I don't know how they are implemeneted. They could \dramatically\
;; effect the analysis. In the worst case the vec calls and vector calls would
;; create entirely new vectors. In the best case they would use Clojure's
;; structure sharing and be fairly performant.


(defn combine
  "The inverse of split, accept this accepts the returned list as arguments, not
   a list. See split for more details."
  [& matrices]
  (let [row-split (count (first matrices))
	col-split (count ((first matrices) 0))
	num-rows (* 2 row-split)
	num-cols (* 2 col-split)]
    (vec
     (for [y (range num-cols)]
       (vec
	(for [x (range num-rows)]
	  (cond
	   (and (< y col-split) (< x row-split))
	   (((nth matrices 0) y) x)
	   (and (< y col-split) (>= x row-split))
	   (((nth matrices 1) y) (- x row-split))
	   (and (>= y col-split) (< x row-split))
	   (((nth matrices 2) (- y col-split )) x)
	   (and (>= y col-split) (>= x row-split))
	   (((nth matrices 3) (- y col-split)) (- x row-split)))))))))

(defn matrix-addition
  "Returns the sume of two matrices A and B.

  This algorithm has a polynomial upper and lower bound of
  n^2 and is undefined when both A and B are not N by M.

  Args:
   A: A matrix which is NxM.
   B: A matrix which is NxM.

  Returns:
   The sum of A and B."
  [A B]
   (let [num-rows (count A) num-cols (count B)]
     (vec (for [y (range num-rows)]
	    (vec (for [x (range num-cols)]
		   (+ ((A y) x) ((B y) x))))))))

(defn strassen-introduction
  "Alright. At this point I think I get the ideas behind Strassen's aglorithm."
  [m1 m2]
  (if (or (>= 2 (count m1)) (>= 2 (count (m1 0))))
    (multiply m1 m2)
    (let [m1-split (split m1)
	  A (nth m1-split 0)
	  B (nth m1-split 1)
	  C (nth m1-split 2)
	  D (nth m1-split 3)
	  m2-split (split m2)
	  E (nth m2-split 0)
	  F (nth m2-split 1)
	  G (nth m2-split 2)
	  H (nth m2-split 3)]
      (combine
       (matrix-addition (strassen-introduction A E)
			(strassen-introduction B G))
       (matrix-addition (strassen-introduction A F)
			(strassen-introduction B H))
       (matrix-addition (strassen-introduction C E)
			(strassen-introduction D G))
       (matrix-addition (strassen-introduction C F)
			(strassen-introduction D H))))))

;; Strasses algoirthm isn't markedly different from the above, so I'm just going
;; to stop here.