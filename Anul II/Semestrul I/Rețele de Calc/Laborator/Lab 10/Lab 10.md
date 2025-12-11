![[Pasted image 20251204123159.png|400]]

Adresa IP 191.10.18.0 | 23
23 - dimensiune netmask

Pentru fiecare subnet (N-uri) trebuie să alocăm un număr de port-uri (?) putere a lui doi mai mare sau egal cu nr hosts + 2

2<sup>32-23</sup> = 2<sup>9</sup> = 512

Spargem dimensiunile
Roșii - folosite, vezi N1-7
Galbene - libere

![[Pasted image 20251204123817.png|400]]

Dimensiunea netmask-ului crește cu 1 per nivel

```
10111111 00001010 00010010 00000000 (adresa noastră IP, 191.10.18.0)
00000000 00000000 00000001 11111111
-----------------------------------
191.10. 00010011 11111111
		19         255
```
Ultima noastră adresă IP valabilă este 191.10.19.255

![[Pasted image 20251204125437.png|400]]

N1 : 256 : 191.10.18.0 | 24
N2 : 64 : 191.10.19.0 | 26
N3 : 32 : 191.10.19.128 | 27
N4 : 64 : 191.10.19.64 | 26
N5 : 8 : 191.10.19.160 | 29
N6 : 4 : 191.10.19.168 | 30
N7 : 4 : 191.10.19.172 | 30
-
L1 : 64 : 191.10.19.152 | 26
L2 : 16 : 191.10.19.176 | 28

Vrem ca adresa, netmask-ul, și default gateway-urile să fie puse automat pe fiecare calculator
```
config terminal
ip dhcp pool MyPool1
default-router 191.10.18.1
network 191.10.19.0 255.255.255.0
exit
exit
wr
```

# **Fă asta pe fiecare router pt data viitoare**

