
![[Pasted image 20251210182936.png|600]]

<hr>

Din R2

| Destination                                                                                      | Gateway                       |
| ------------------------------------------------------------------------------------------------ | ----------------------------- |
| N1 - 191.10.18.0\|24                                                                             | 191.10.19.161                 |
| N3                                                                                               | 191.10.19.163                 |
| N4                                                                                               | 191.10.19.161 / 191.10.19.163 |
| N5                                                                                               | Directly Connected            |
| N6                                                                                               | 191.10.19.161                 |
| N7                                                                                               | 191.10.19.163                 |
| N2                                                                                               | Directly Connected            |
| 0.0.0.0\|0<br>Gateway of Last Resort<br>(Default Gateway)<br>(Internetul)<br>(Norul din imagine) | 191.10.19.161 / 191.10.19.163 |

Pe router
```
enable
show ip route
=================
Gateway of last resort is not set

  

191.10.0.0/16 is variably subnetted, 3 subnets, 3 masks

C 191.10.18.0/24 is directly connected, FastEthernet1/0

C 191.10.19.160/29 is directly connected, FastEthernet2/0

C 191.10.19.168/30 is directly connected, FastEthernet0/0
=================

config terminal
router rip
network 191.10.18.0
network 191.10.19.160
network 191.10.19.168
version 2
exit
exit
wr
show ip route
```

Adăugăm Gateway of Last Resort manual
```
conf t
ip route 0.0.0.0 0.0.0.0 191.10.19.130
exit
enable
wr
show ip route
```
în fiecare router cu ip-ul spre router-ul în drum spre "internet", spre nor
Mai puțin router-ul conectat direct cu internetul
