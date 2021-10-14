package com.dsp.inmar;

public class PersonPublisher {
    public PersonPublisher() {
    }

    public void publishMessage(Person person) {
        System.out.println("FirstName : " + person.getFirstName() + ", Last Nme: " + person.getLastName());
    }
}
