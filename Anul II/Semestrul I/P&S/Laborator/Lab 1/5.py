# Avem 7 scaune mereu libere
# s1 s2 s3 s4 s5 s6 s7
# Pentru a avea minim un scaun liber între fiecare 2 persoane, singurele poziții pe care putem pune câte o persoană este între acestea
# _ s1 _ s2 _ s3 _ s4 _ s5 _ s6 _ s7 _
# 8 poziții posibile unde pot să stea 5 oameni
# Aranjamente de 8 luate câte 5

from math import perm

print(perm(8, 5))