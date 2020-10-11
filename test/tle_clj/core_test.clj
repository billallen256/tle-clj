; vim: expandtab tabstop=2 shiftwidth=2
(ns tle-clj.core-test
  (:require [clojure.test :refer :all]
            [tle-clj.core :refer :all]
            [clojure.math.numeric-tower :as math]))

(deftest test-create-string-list
  (testing "Breaks TLE into list of strings"
    (let [parsed-tle (parse-tle "1 25544U 98067A   20270.89265910  .00001975  00000-0  44284-4 0  9999"
                                "2 25544  51.6439 205.2096 0001346  99.6014   5.3272 15.48789727247782")]
      (is (map? parsed-tle))
      (is (= (count parsed-tle) 19)))))

(deftest test-map-line1
  (testing "Generates map from line 1 of TLE"
    (let [map1 (map-line1 "1 25544U 98067A   20270.89265910  .00001975  00000-0  44284-4 0  9999")]
      (is (map? map1))
      (is (= (get map1 :sat-number) "25544"))
      (is (= (get map1 :id-launch-piece) "A  "))
      (is (float? (get map1 :mean-motion-derivative))))))

(deftest test-map-line2
  (testing "Generates map from line 2 of TLE"
    (let [map2 (map-line2 "2 25544  51.6439 205.2096 0001346  99.6014   5.3272 15.48789727247782")]
      (is (map? map2))
      (is (= (get map2 :inclination) " 51.6439"))
      (is (= (get map2 :excentricity) "0001346")))))

(deftest test-tle-significand
  (testing "Pulls out significand portion of TLE decimal"
    (let [bstar "-11606-4"]
      (is (= (tle-significand bstar) "11606")))))

(deftest test-tle-exponent
  (testing "Pull out exponent portion of TLE decimal"
    (let [bstar "-11606-4"]
      (is (= (tle-exponent bstar) "-4")))))

(deftest test-read-tle-decimal
  (testing "Reads a TLE encoded decimal number"
    (let [bstar "-11606-4"]
      (is (= (read-tle-decimal bstar) (Float/parseFloat "-1.1606e-5"))))))

(deftest test-parse-int-or-default
  (testing "Parses an integer or returns the supplied default"
    (is (= (parse-int-or-default " " 0) 0))
    (is (= (parse-int-or-default "8675309" 0) 8675309))))
