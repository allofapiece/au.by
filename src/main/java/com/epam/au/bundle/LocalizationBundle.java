package com.epam.au.bundle;

import java.util.ResourceBundle;


public class LocalizationBundle {
        private static volatile LocalizationBundle instance;
        private ResourceBundle bundle = ResourceBundle.getBundle(BundleNamesStore.LOCALIZATION_BUNDLE);

        private LocalizationBundle() {
        }

        public static LocalizationBundle getInstance() {
            if (instance == null)
                synchronized (LocalizationBundle.class) {
                    if (instance == null)
                        instance = new LocalizationBundle();
                }
            return instance;
        }

        public String getValue(String key) {
            return bundle.getString(key);
        }
}
