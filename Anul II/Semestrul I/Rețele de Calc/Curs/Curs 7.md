# Adresarea IP

- adresa IP
- masca de rețea (net mask, subnet mask)
- gateway, default gateway, GW, next router
- DNS

Gateway - adresa IP a router-ului responsabil să trimită pachetele din rețeaua locală spre internet
DNS - translatare nume de calculatoare / pachete (?)

Exemplu adrese IP din aceeași rețea:
*192.168.1*.101
*192.168.1*.150
*192.168.1*.1

Masca de rețea ne zice cât de similare trebuie să fie adresele IP din aceeași rețea locală
Mască de rețea pentru adresele de mai sus:
*255.255.255*.0

Exemplu 2 adrese IP din aceeași rețea:
*172.30*.x.y
*172.30*.0.4
*172.30*.116.200
*172.30*.0.1
Masca de rețea asociată:
*255.255*.0.0

Adresele ip care pe lângă mască sunt formate doar din 0 sau 255 sunt innacesibile, sunt rezevrate
Prima adresă ip după cea formată din mască urmată de 0 (cea cu 1) este rezervată router-ului

Pentru masca *255.255.255*.0, adresa *192.168.1*.0 se numește adresa de rețea, iar *192.168.1*.255 este adresa de broadcast

Dacă facem un "și" pe biți între masca de rețea și o adresă IP din rețeaua respectivă, primim adresa de rețea

Mască *255*.0.0.0 - Rețele de tip (a.k.a. clasă) A
Mască *255.255*.0.0 - Rețele de tip B
Mască *255.255.255*.0 - Rețele de tip C

