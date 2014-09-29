(ns clj-chirp.reading-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.reading :refer :all]
            [clj-time.core :as time]))

(describe "clj-chirp.reading"
          (with content "post")
          (with timestamp (time/date-time 2014))
          (with user "user")
          (describe "There are no user posts."
                    (it "Returns nothing."
                        (should= {}
                                 (read-user-posts [] @user))))
          (describe "There is no match for the user."
                    (it "Returns nothing."
                        (should= {}
                                 (read-user-posts
                                   [{:user-name @user
                                     :posts-values [{:content @content :timestamp @timestamp}]}]
                                   "not existing user"))))
          (describe "There are posts."
                    (it "Read user posts."
                        (should= {:user-name @user
                                  :posts-values [{:content @content :timestamp @timestamp}
                                                 {:content @content :timestamp @timestamp}]}
                                 (read-user-posts
                                   [{:user-name @user
                                     :posts-values [{:content @content :timestamp @timestamp}
                                                    {:content @content :timestamp @timestamp}]}]
                                   @user))))
          (describe "There are many users."
                    (with diffrent-user "diffrent user")
                    (it "Correct user is read."
                        (should= {:user-name @user :posts-values [{:content @content :timestamp @timestamp}]}
                                 (read-user-posts [{:user-name @diffrent-user
                                                    :posts-values [{:content @content :timestamp @timestamp}]}
                                                   {:user-name @user
                                                    :posts-values [{:content @content :timestamp @timestamp}]}]
                                                  @user)))))