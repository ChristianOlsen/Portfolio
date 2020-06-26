from random import randint
from celle import Celle

class Spillebrett:
    
    def __init__(self, rader, kolonner):
        self._rader = rader
        self._kolonner = kolonner
        self._rutenett = []
        self._generasjonsnummer = 0
        self._generer()
    
    # Tegner spillbrettet
    def tegnBrett(self):
        spillbrett = ''
        
        print('\n'*10)
        for y in range(self._rader):        # for hver rad i rutenettet
            for x in range(self._kolonner): # for hver element i hver rad
                spillbrett += self._rutenett[y][x].hentStatusTegn()    # så printes statustegnet for cellen med dete koordinatet
            spillbrett += '\n' 
        print(spillbrett)
        # printer genearsjonsnummber og ant levende celler
        print(f'Generasjon: {self._generasjonsnummer} - Antall levende celler: {self.finnAntallLevende()}')
    
    # Oppdaterer status til celler
    def oppdatering(self):
        skalDo = []     # fylles med alle celler i rutenettet som skal dø i neste generasjon
        skalLeve = []   # cellene som skal leve i neste gen
        
        for y in range(self._rader):
            for x in range(self._kolonner):         # går gjennom hvert eneste element i rutenettet
                antLevendeNaboer = 0                # resetter antall levende naboer til gitte cellen
                for celle in self.finnNabo(y, x):   # for hver celle i listen av naboer til gitte cellen
                    if celle.erLevende():           # hvis nabocellen lever, 
                        antLevendeNaboer += 1       # så øker antall levende naboer med 1
                        
                if self._rutenett[y][x].erLevende():    # hvis cellen lever
                    if antLevendeNaboer == 2 or antLevendeNaboer == 3:  # og har tre levende eller to naboer, så skal cellen leve i neste gen
                        skalLeve.append(self._rutenett[y][x])
                    else:                               # hvis ikke cellen har tre levende naboer, skal den dø
                        skalDo.append(self._rutenett[y][x])  
                else:                                   # hvis cellen er død
                    if antLevendeNaboer == 3:           # og den har akkurat 3 levende naboer, så lever den videre
                        skalLeve.append(self._rutenett[y][x])
                    else:                               # hvis cellen er død og ikke har 3 levende naboer, fortsetter den å være død
                        skalDo.append(self._rutenett[y][x])  
                                               
        for celle in skalLeve:          # endrer status for alle celler som skal leve i neste gen
            celle.settLevende()
        for celle in skalDo:            # endrer status for alle celler som skal dø
            celle.settDoed()
        
        self.tegnBrett()                # tegner spillbrettet med endringene
        self._generasjonsnummer += 1    # øker telleren for generasjon

    # Finner antall levende celler i spillet
    def finnAntallLevende(self):
        antLevende = 0                  # resetter antall levende celler
        for y in range(self._rader):    
            for x in range(self._kolonner): # går gjennom hvert eneste element i rutenettet
                if self._rutenett[y][x].erLevende():    # hvis gitte celle lever
                    antLevende += 1         # øker antall levende med én
        return antLevende                   # returnerer tallet for antall levende celler i rutenettet
    
    # Fyller spillbrettet med celler
    def _generer(self):   
        for y in range(self._rader):        # fyller rutenettet med lister som skal representere rader, y-retning
            self._rutenett.append([])
            for _ in range(self._kolonner): # skal fylle hver rad med celler i x-retning. _ brukes isteden for x for vi har ikke nytte av variabelen her
                seed = randint(0,2)         # tilfeldig taLL 0-2
                if seed == 0:               # 1/3 sjangse for at cellen spawner levende
                    self._rutenett[y].append(Celle(True))
                else:                       # 2/3 sjangse for at cellen spawner død
                    self._rutenett[y].append(Celle(False))

    # Finner naboer til gitt celle
    def finnNabo(self, rad, kolonne):
        naboer = []             # liste som skal fylles med "nabocellene" til gitt celle
        i = [-1,0,1]            # x- og y-indeks for hvor naboen er i forhold til cellen
        
        for y in i:             # går gjennom -1, 0 og 1
            naboRad = rad + y   # y-koordinatet til naboraden til cellen
            for x in i:         # går gjennom -1, 0 og 1
                naboKolonne = kolonne + x   # x-koordinatet til nabokolonnen til cellen
                # hvis naboen ikke er utenfor kanten til spillrammen/rutenettet. f.eks negativ indeks for listene
                if naboRad >= 0 and naboRad <= self._rader-1 and naboKolonne >= 0 and naboKolonne <= self._kolonner-1:
                    # hvis naboen ikke er har koordinatet til selve cellen vi skal finne naboer til
                    if self._rutenett[rad][kolonne] != self._rutenett[naboRad][naboKolonne]:
                        # så legges nabocellen i lista for naboceller
                        naboer.append(self._rutenett[naboRad][naboKolonne])
        return naboer   # returnerer en liste for naboer til en celle med gitte koordinater
     
    
