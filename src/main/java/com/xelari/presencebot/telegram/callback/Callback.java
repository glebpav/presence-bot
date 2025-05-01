package com.xelari.presencebot.telegram.callback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Callback {

    private CallbackType callbackType;
    private CallbackDataCache.DataKey dataKey;

}
