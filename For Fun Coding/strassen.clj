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
    (for [y (range num-rows)]
      (for [x (range num-cols)]
	(C_yx m1 m2 y x num-rows num-cols)))))

;; I want this to return four vectors.
(defn split
  "Returns A split through the middle.

  Given a matrix nxm where both n and m are powers of two this
  splits the matrix into fourths with a lower bound of n^2. Though
  the upper bound on this function has not been calculated, it is
  likely to be around n^2 as well.

  Args:
    A: A NxM matrix to split into fourths which has the property that
    both n and m are powers of two.

  Returns:
    The matrix split through the middle into four new matrices such that
    [[1 2] [3 4]] becomes [[1] [2] [3] [4]]."
  [A]
  (let [num-rows (count A)
	num-cols (count (A 0))
	row-split (/ num-cols 2)
	col-split (/ num-rows 2)]
    [
     (for [y (range col-split)]
       (for [x (range row-split)]
	 ((A y) x)))
     (for [y (range col-split)]
       (for [x (range row-split num-cols)]
	 ((A y) x)))
     (for [y (range col-split num-rows)]
       (for [x (range row-split)]
	 ((A y) x)))
     (for [y (range col-split num-rows)]
       (for [x (range row-split num-cols)]
	 ((A y) x)))]))

(defn matrix-addition
  "Returns the sume of two matrices A and B.

  This algorithm has a polynomial upper and lower bound of
  n^2 and is undefined when both A and B are not N by M.

  Args:
   A: A matrix which is NxM.
   B: A matrix which is NxM.

  Returns:
   The sume of A and B."
  [A B]
  (let [num-rows (count A) num-cols (count B)]
    (for [y (range num-rows)]
      (for [x (range num-cols)]
	(+ ((A y) x) ((B y) x))))))