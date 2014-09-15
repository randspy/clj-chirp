(ns clj-chirp.following-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.following :refer :all]))

(describe "User follows others."
          (it "User does not exist."
              (should= []
                      (follows [] {:user "user bob" :content ""})))
          (it "Followed user does not exist."
              (should= [{:user "user bob"}]
                       (follows [{:user "user bob"}] {:user "user bob" :content "user robert"})))
          (it "Can not follow itself"
              (should= [{:user "user bob"}]
                       (follows [{:user "user bob"}] {:user "user bob" :content "user bob"})))
          (it "Followed user name is added."
              (should= [{:user "user bob" :follows ["user robert"]} {:user "user robert"}]
                       (follows [{:user "user bob"} {:user "user robert"}]
                                {:user "user bob" :content "user robert"})))
          (it "Second followed user name is added."
              (should= [{:user "user bob" :follows ["user alice" "user robert"]}
                        {:user "user robert"}
                        {:user "user alice"}]
                       (follows [{:user "user bob" :follows ["user robert"]}
                                 {:user "user robert"}
                                 {:user "user alice"}]
                                {:user "user bob" :content "user alice"})))
          (it "Second followed user name is added."
              (should= [{:user "user bob" :follows ["user alice" "user robert"]}
                        {:user "user robert"}
                        {:user "user alice"}]
                       (follows [{:user "user bob" :follows ["user robert"]}
                                 {:user "user robert"}
                                 {:user "user alice"}]
                                {:user "user bob" :content "user alice"})))
          (it "User should not be followed twice."
              (should= [{:user "user bob" :follows ["user robert"]}
                        {:user "user robert"}]
                       (follows [{:user "user bob" :follows ["user robert"]}
                                 {:user "user robert"}]
                                {:user "user bob" :content "user robert"}))))