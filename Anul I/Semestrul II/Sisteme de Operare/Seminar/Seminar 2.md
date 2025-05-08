<sub>Seminar 2</sub>

`*` - wildcard, orice șir de caractere dar nu punctul de la începutul numelui fișierului
`?` - wildcard, orice caracter, dar nu punctul de la începutul numelui fișierului
`[abc]` - range de caractere
`[!abc]` - negarea range-ului de caractere

Wildcard-uri care să găsească toate fișierele C (.c și .C), și toate filele ascunse (cu `.` în față?)
`ls *.[cCh]`
`ls -d .*`

Asta caută doar în directorul curent
Ca să le găsim pe toate facem
`find ~ -type f -name "*.c"`