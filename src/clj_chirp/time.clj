(ns clj-chirp.time
  (:require [clj-time.local :as local-time]
            [clj-time.core :as time]))

(defn current-time []
  (local-time/local-now))

(defn- date-diff [first-date second-date]
  (if (time/before? first-date second-date)
    (time/interval first-date second-date)
    (time/interval second-date first-date)))

(defn- last-unit? [sequence]
  (nil? (seq (rest sequence))))

(defn- corrent-unit-for-time-diff [diff-in-unit]
  (not= 0 diff-in-unit))

(defn- time-difference-with-correct-unit
  "Loops over functions till finds time resolution where value is bigger than 0."
  [units time-diff]
  (loop [units units]
    (let [unit-function (:function (first units))
          diff-in-unit (unit-function time-diff)]
      (if (or (corrent-unit-for-time-diff diff-in-unit)
              (last-unit? units))
        {:time-unit (:text (first units)) :time-diff diff-in-unit}
        (recur (rest units))))))

(defn time-diffrence [date-older date-newer]
  (let [diff (date-diff date-older date-newer)
        time-diff (time-difference-with-correct-unit
                  [{:function time/in-years :text "year"}
                   {:function time/in-months :text "month"}
                   {:function time/in-days :text "day"}
                   {:function time/in-hours :text "hour"}
                   {:function time/in-minutes :text "minute"}
                   {:function time/in-seconds :text "second"}]
                  diff)]
   time-diff))