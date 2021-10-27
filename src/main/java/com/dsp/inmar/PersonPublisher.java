package com.dsp.inmar;

import java.util.List;

public class PersonPublisher {
    public PersonPublisher() {
    }

    public void publishMessage(List<Person> persons) {
        for (Person person : persons) {
            System.out.println(person.getFirstName() + " " + person.getLastName());
        }
    }
}

