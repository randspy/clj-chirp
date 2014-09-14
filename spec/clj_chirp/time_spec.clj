(ns clj-chirp.time-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.time :refer :all]
            [clj-time.core :as time]))

(describe "Diffrence in time."
          (with basic-date (time/date-time 2014))
          (it "There is no diffrence between dates."
              (should= {:time-unit "second" :time-diff 0}
                       (time-diffrence @basic-date @basic-date)))
          (describe "There is a diffrence in time."
              (it "In seconds."
                  (should= {:time-unit "second" :time-diff 2}
                           (time-diffrence @basic-date
                                           (time/date-time 2014 01 01 00 00 02))))
              (it "In minutes."
                  (should= {:time-unit "minute" :time-diff 4}
                           (time-diffrence @basic-date
                                           (time/date-time 2014 01 01 00 04))))
              (it "In hours."
                  (should= {:time-unit "hour" :time-diff 2}
                           (time-diffrence @basic-date
                                           (time/date-time 2014 01 01 02))))
              (it "In days."
                  (should= {:time-unit "day" :time-diff 3}
                           (time-diffrence @basic-date
                                           (time/date-time 2014 01 04))))
              (it "In months."
                  (should= {:time-unit "month" :time-diff 4}
                           (time-diffrence @basic-date
                                           (time/date-time 2014 05))))
              (it "In years."
                  (should= {:time-unit "year" :time-diff 10}
                           (time-diffrence @basic-date
                                           (time/date-time 2024)))))
          (it "Reverced dates will give this same value."
              (should= {:time-unit "second" :time-diff 2}
                       (time-diffrence (time/date-time 2014 01 01 00 00 02)
                                       @basic-date))))
