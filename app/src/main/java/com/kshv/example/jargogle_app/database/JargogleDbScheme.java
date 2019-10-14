package com.kshv.example.jargogle_app.database;

public class JargogleDbScheme {
    public static class JargogleTable{
        public static final String NAME = "jargogles";

        public static final class JargogleCols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATA = "data";
            public static final String ENCODED = "encoded";
            public static final String CHAIN_LEN = "chain_len";
            public static final String CHAIN_SEED = "chain_seed";
            public static final String PASSWD = "passwd";
        }
    }
}
