package edu.esprit.tests;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class ListVoices {
    public static void main(String[] args) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice[] voices = voiceManager.getVoices();
        System.out.println("aaaa");

        for (Voice voice : voices) {
            System.out.println("bbb");
            System.out.println("Nom de la voix : " + voice.getName());
        }
    }
}
