/**
 * sekiro多用于代码注入环境，所以需要考虑依赖精简性，否则注入代码和宿主代码依赖相同的API，则可能由于API版本不一致导致隐性bug<br>
 * sekiro依赖的netty在后来也需要单独抽离
 */
package com.h0p1.api.fastjson;