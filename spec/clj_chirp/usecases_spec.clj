(ns clj-chirp.usecases-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.usecases :refer :all]
            [clj-chirp.content :as content]
            [clj-chirp.posting :as posting]
            [clj-time.core :as time]))

(describe "When posting usecase is executed."
          (with timestamp (time/date-time 2014))
          (with! post (atom [{:user "user robert"
                              :posts [{:content "post" :timestamp (time/date-time 2014)}]}]))
          (it "Posts are updated."
              (should= [{:user "user robert"
                         :posts [{:content "post" :timestamp @timestamp}]}
                        {:user "user bob"
                         :posts [{:content "post" :timestamp @timestamp}]}]
                       (usecase-execution @post "user bob -> post" @timestamp))))