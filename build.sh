#!/bin/bash

[[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]] && source "$HOME/.sdkman/bin/sdkman-init.sh"

for project in $(ls -1); do
    test -f $project && continue;
    
    cd $project
    echo "$project"
    [[ -s .sdkrc ]] && sdk env 
    test -e pom.xml && ./mvnw clean verify
    test -e buil.gradle && ./gradlew clean check
    cd ..
done;



