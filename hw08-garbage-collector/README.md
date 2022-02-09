# Garbage Collector

## Task
1. Execute the application with 256M heap. Add execution time in a table.
2. Execute the application with 2G heap. Add execution time in the table.
3. Find the maximum heap size time doesn't become more.
4. Optimize the application to make it work with minimal heap size.
5. Repeat measurements.

| Heap Size | milliseconds | Seconds | Optimized
| ------------- |:-------------:|:----:|:---:|
| 256M | `out of memory` | `out of memory` | no |
| 512M | 10442 | 10 | no |
| 768M | 9541 | 9 | no |
| 1G | 9345 | 9 | no |
| 2G | 8746 | 8 | no |
| 4G | 8707 | 8 | no |
| 8G | 9277 | 9 | no |
| 64M | 867 | 0 | yes |
| 128M | 806 | 0 | yes |
| 256M | 738 | 0 | yes |
| 512M | 769 | 0 | yes |
| 768M | 786 | 0 | yes |
| 1G | 822 | 0 | yes |
| 2G | 932 | 0 | yes |
| 4G | 914 | 0 | yes |
| 8G | 834 | 0 | yes |

Before optimization the best heap size is 2G. \
After the optimization in [this](https://github.com/shurupov/2021-12-otus-java-professional-shurupov/commit/03285e28c7f8c132ec68697c5729ddecf92cf2e7#diff-e8fbf648f04dc10b6894d551af0e8c0b741b7e43a80d0b2414a70f410ed32c01R23) and [this change](https://github.com/shurupov/2021-12-otus-java-professional-shurupov/commit/4b29f5c0a4064755f4b75c2c347d11b5554f9d8a#diff-baf4b6947b0e5609506225d689b6887e8c210a3661eeefb7e36ccd2410965aeb) the best heap size is 256M.



