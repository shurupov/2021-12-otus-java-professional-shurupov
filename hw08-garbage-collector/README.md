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
| 256M | 8395 | 8 | yes |
| 512M | 7635 | 7 | yes |
| 768M | 7420 | 7 | yes |
| 1G | 7217 | 7 | yes |
| 2G | 7147 | 7 | yes |
| 4G | 5790 | 5 | yes |
| 8G | 4980 | 4 | yes |

