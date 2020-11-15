(ns ^:figwheel-always cc-luhn-reagent.core.tests
  (:require
   [cljs.test :refer-macros [deftest is testing run-tests]]
   [cc-luhn-reagent.core :as c]))

(deftest test-numbers
  (is (= 1 1)))

(deftest test-double-number
  (is (= (c/double-number 1) 2))
  (is (= (c/double-number 2) 4))
  (is (= (c/double-number 3) 6))
  (is (= (c/double-number 4) 8))
  (is (= (c/double-number 5) 1))
  (is (= (c/double-number 6) 3))
  (is (= (c/double-number 7) 5))
  (is (= (c/double-number 8) 7))
  (is (= (c/double-number 9) 9))
  )

(deftest test-double-odd
  (is (= (c/double-odd ["1" "2" "3" "4"]) '(1 4 3 8))))

(deftest test-luhn-valid
  (is (c/luhn-valid? "4539148803436467"))
  (is (not (c/luhn-valid? "8273123273520569")))
  )

(deftest test-event-handler
  (is (= (c/event-handler "4" (atom "")) "4"))
  (is (= (c/event-handler "4b" (atom "")) "4")))

(deftest test-input-style
  (is (= (c/input-style (atom "4539148803436467")) "valid"))
  (is (= (c/input-style (atom "8273123273520569")) "invalid")))

(deftest test-header
  (is (= (c/header) [:div.header "Ultimate cc-checker"])))

(deftest footer
  (is (= (c/footer)
         [:div.footer
          "Learn more about "
          [:a {:href "https://en.wikipedia.org/wiki/Luhn_algorithm"} "Luhn algorithm"]])))

(run-tests)
