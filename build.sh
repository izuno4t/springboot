#!/bin/bash

[[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]] && source "$HOME/.sdkman/bin/sdkman-init.sh"

for project in $(ls -1); do
    test -f $project && continue;
    
    cd $project
    echo "Build $project"
    [[ -s .sdkmanrc ]] && sdk env 
    test -e pom.xml && ./mvnw -q clean verify > /dev/null
    test -e buil.gradle && ./gradlew -q clean check > /dev/null
    cd ..
done



