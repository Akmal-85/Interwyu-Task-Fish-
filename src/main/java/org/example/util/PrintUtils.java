package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.model.Fish;


@UtilityClass
public class PrintUtils {
    public void printMarriage(Fish male, Fish female, int maleChild,int femaleChild){
        System.out.println(String.format("%s - %d yoshli - %s baliq bilan %s - %d yoshli - %s baliq turmush qurushdi va ularning %d ta ogil va %d ta qiz farzandi bor ",
                male.getName(), male.getBirthAge(), male.getGender(),
                female.getName(), female.getBirthAge(), female.getGender(), maleChild, femaleChild
        ));

    }

    public void printDead(Fish fish){
        System.out.printf("%s baliq  %d yosh vafot etgan \n",fish.getName(),fish.getMaxAge());
    }

}
