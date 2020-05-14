package com.scripts;

import android.os.Bundle;

public interface InterfaceForFragments {

    int KEY_POSITION = 1;
    String KEY_MODE="KEY_SELECTED_COUNTRY";

    void onActionInFragment(Bundle bundle);
}
