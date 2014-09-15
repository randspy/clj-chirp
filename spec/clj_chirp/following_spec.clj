(ns clj-chirp.following-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.following :refer :all]))

(describe "User follows others."
          (with user "user")
          (with followed-user "followed user")
          (it "User does not exist."
              (should= []
                       (follows [] {:user-name @user :content ""})))
          (it "Followed user does not exist."
              (should= [{:user-name @user}]
                       (follows [{:user-name @user}] {:user-name @user :content @followed-user})))
          (it "Can not follow itself"
              (should= [{:user-name @user}]
                       (follows [{:user-name @user}] {:user-name @user :content @user})))
          (it "Followed user name is added."
              (should= [{:user-name @user :follows [@followed-user]} {:user-name @followed-user}]
                       (follows [{:user-name @user} {:user-name @followed-user}]
                                {:user-name @user :content @followed-user})))
          (it "User should not be followed twice."
              (should= [{:user-name @user :follows [@followed-user]}
                        {:user-name @followed-user}]
                       (follows [{:user-name @user :follows [@followed-user]}
                                 {:user-name @followed-user}]
                                {:user-name @user :content @followed-user})))
          (describe "More users are folloed"
                    (with alredy-followed-user "alredy followed user")
                    (it "Second followed user name is added."
                        (should= [{:user-name @user :follows [@alredy-followed-user @followed-user]}
                                  {:user-name @alredy-followed-user}
                                  {:user-name @followed-user}]
                                 (follows [{:user-name @user :follows [@alredy-followed-user]}
                                           {:user-name @alredy-followed-user}
                                           {:user-name @followed-user}]
                                 {:user-name @user :content @followed-user})))))
