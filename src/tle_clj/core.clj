; vim: expandtab tabstop=2 shiftwidth=2

(ns tle-clj.core
  (:require [clojure.string :as str]
            [clojure.math.numeric-tower :as math]))

(defn tle-has-sign-prefix
  "Returns whether a TLE decimal number string has a sign prefix."
  [number-string]
  (let [beginning (subs number-string 0 1)]
    (or (= beginning "-") (= beginning "+"))))

(defn tle-sign-prefix
  "Gets the sign at the beginning of a TLE decimal number string."
  [number-string]
  (let [beginning (subs number-string 0 1)]
    (if (= beginning "-") "-" "+")))

(defn tle-exponent
  "Get the exponent portion of a TLE decimal number string."
  [number-string]
  (let [beginIndex (max (.lastIndexOf number-string "+") (.lastIndexOf number-string "-"))]
    (subs number-string beginIndex)))

(defn tle-significand
  "Gets the significand portion of a TLE decimal number string."
  [number-string]
  (let [beginIndex (if tle-has-sign-prefix 1 0)
        endIndex (max (.lastIndexOf number-string "+") (.lastIndexOf number-string "-"))]
    (subs number-string beginIndex endIndex)))

(defn read-tle-decimal
  "Reads TLE numbers with assumed decimal"
  [number-string]
  (let [sign (tle-sign-prefix number-string)
        significand (str "0." (tle-significand number-string))
        exponent (tle-exponent number-string)]
    (Float/parseFloat (str sign significand "e" exponent))))
;    (* significand
;       (math/expt 10 exponent)
;       (if (= sign "-") -1.0 1.0))))

(defn map-line1
  "Generates a map for TLE line 1"
  [line1]
  {
    :sat-number (subs line1 2 7)
    :classification (subs line1 7 8)
    :id-launch-year (subs line1 9 11)
    :id-launch-number (subs line1 11 14)
    :id-launch-piece (subs line1 14 17)
    :epoch-year (subs line1 18 20)
    :epoch-day (subs line1 20 32)
    :mean-motion-derivative (Float/parseFloat (subs line1 33 43))
    :mean-motion-second-derivative (read-tle-decimal (subs line1 44 52))
    :bstar (subs line1 53 61)
    :ephemeris-type (subs line1 62 63)
    :element-number (subs line1 64 68)
  })

(defn map-line2
  "Generates a map for TLE line 2"
  [line2]
  {
    :inclination (subs line2 8 16)
    :right-ascension (subs line2 17 25)
    :excentricity (subs line2 26 33)
    :arg-perigee (subs line2 34 42)
    :mean-anomaly (subs line2 43 51)
    :mean-motion (subs line2 52 63)
    :orbits (subs line2 63 68)
  })

(defn parse-tle
  "Parses a TLE"
  [line1 line2]
  (merge (map-line1 line1) (map-line2 line2)))
