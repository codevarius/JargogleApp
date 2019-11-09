package com.kshv.example.jargogle_app.model;

import java.util.ArrayList;
import java.util.List;

public class JargogleCodeManager {
    private char[] alphabet;
    private Chain jargogleChain;
    public static final int ENCODING = 1, DECODING = 0;

    public JargogleCodeManager(long chainLen, long chainSeed, int encodingDirection) {
        alphabet = new char[]{
                'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x',
                'y', 'z'
        };
        jargogleChain = new Chain(chainLen, chainSeed, encodingDirection);

    }

    public Chain getJargogleChain() {
        return jargogleChain;
    }

    public class Chain {
        private List<ChainLink> chain;

        Chain(long chainLen, long chainSeed, int encodingDirection) {
            List<Character> chainSeedList = new ArrayList<>();
            for (char item : Long.toString(chainSeed).toCharArray()) {
                chainSeedList.add(item);
            }

            if (chainSeedList.size() < chainLen) {
                long delta = chainLen - chainSeedList.size();
                for (int i = 0; i < delta; i++) {
                    chainSeedList.add('0');
                }
            }

            chain = new ArrayList<>();

            for (int i = 0; i < chainLen; i++) {
                if (encodingDirection == JargogleCodeManager.ENCODING) {
                    chain.add(new Caesar(Integer.parseInt(chainSeedList.get(i).toString())));
                } else {
                    chain.add(new Caesar(-Integer.parseInt(chainSeedList.get(i).toString())));
                }
            }
        }

        public String processChain(String input) {
            String output = input;

            for (ChainLink chainLink : chain) {
                output = chainLink.encode(output);
            }

            return output;
        }
    }

    public interface ChainLink {

        String encode(String input);
    }

    public class Caesar implements ChainLink {
        private int shift;

        Caesar(int shift) {
            this.shift = shift;
        }

        @Override
        public String encode(String input) {
            char[] rawInput = input.toCharArray();
            System.out.println(input);

            for (int i = 0; i < rawInput.length; i++) {
                for (int j = 0; j < alphabet.length; j++) {
                    if (rawInput[i] == alphabet[j]) {
                        int validShift = j + shift;
                        if (validShift > (alphabet.length - 1)) {
                            validShift = validShift - alphabet.length;
                        }
                        if (validShift < 0) {
                            validShift = alphabet.length + validShift;
                        }
                        rawInput[i] = alphabet[validShift];
                        break;
                    }
                }
            }

            return String.valueOf(rawInput);
        }
    }
}
