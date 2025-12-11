
# Tipuri de trafic

1. Unicast (1 - 1)
2. Broadcast (1 - toți)
3. Multicast (1 - n)
	- *Arbore de acoperire multicast*, pe un exemplu: Un server transmite un livestream către doi studenți dintr-un cămin. În loc sădea unicast spre fiecare, transmite informația o singură dată spre router, iar apoi de acolo își dă "split" către cei doi studenți. Similar, un server din București ce transmite stații de radio către Cluj ar putea să își transmită traficul de la router la router, în loc de individual la fiecare ascultător din Cluj. Pe hârtie sună bine, în practică intervin multe probleme, și majoritatea serverelor de streaming folosesc Unicast
4. Anycast (1 - oricare primește)
	- Unul țipă, speră să îl audă cineva

# Topologii de rețele

1. **Rețele cu topologie liniară (bus)**
![[Pasted image 20251106083740.png|700]]
A vrea să transmită ceva la D
De la A se difuzează informația pe tot firul de rețea, fiecare client având acces la date și luând decizia dacă le dorește

Dacă se întâmpla ceva cu mediul de transmisie,
![[Pasted image 20251106084035.png|700]]
cum ar fi o rupere de acestg tip, A nu mai poate comunica cu B sau cu C
La capetele acestui fir se mai pun terminatoare sau "dopuri" (*termen informal, nu îl folosi*) pentru a închide rețeaua
Într-un capăt al rețelei se mai pune un router (altă căsuță notată cu "R") ca să se poată comunica și cu internetul

2. **Rețele cu topologie stea**
![[Pasted image 20251106085458.png|700]]

3. **Rețele cu topologie stea extinsă**
![[Pasted image 20251106093005.png|700]]

4. Topologie de graf incomplet