;; Joshua Cole Copyright 2011
;; Release under the MIT License

;; In the famous CLRS algorithms textbook there is a section which discusses
;; matrix operations. Herein are some of my attempts at translating some of
;; these matrix operations into Clojure.

(def test-matrix [[1 2] [1 2]])

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