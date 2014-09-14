(ns clj-chirp.reading-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.reading :refer :all]
            [clj-time.core :as time]))

(describe "Reads user posts."
          (with timestamp (time/date-time 2014))
          (it "There is no user posts"
              (should= {}
                       (read-user-posts [] "user bob")))
          (it "There is a post"
              (should= {:user "user bob" :posts [{:content "post" :timestamp @timestamp}
                                                 {:content "other post" :timestamp @timestamp}]}
                       (read-user-posts [{:user "user bob"
                                          :posts [{:content "post" :timestamp @timestamp}
                                                  {:content "other post" :timestamp @timestamp}]}]
                                        "user bob")))
          (it "There are many users"
              (should= {:user "user bob" :posts [{:content "post" :timestamp @timestamp}]}
                       (read-user-posts [{:user "user robert"
                                          :posts [{:content "post" :timestamp @timestamp}]}
                                         {:user "user bob"
                                          :posts [{:content "post" :timestamp @timestamp}]}]
                                        "user bob"))))