#!/bin/bash

java -cp "./lib/*:./bin/" com.coducation.smallbasic.VersionInfo
java -cp "./lib/*:./bin/" com.coducation.smallbasic.gui.MySmallBasicGUI
cd sbparser
sbparser-exe.exe