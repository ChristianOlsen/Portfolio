from spillebrett import Spillebrett

def main():
    print('Game of Life - et zero player spill')
    game_of_life = Spillebrett(int(input('Hoyde: ')), int(input('Bredde: '))) # oppretter et objekt for spillbrettet med gitt størrelse
    game_of_life.oppdatering()  # printer første generasjon av spillet
    kommando = input('Trykk enter for aa fortsette. Skriv inn q og trykk enter for aa avslutte.')

    while kommando != 'q':  # så lenge man ikke "quitter" kan man skrive inn kommandoer
        if kommando == '':  # hvs kun enter blir satt inn, oppdateres spillbrettet
            game_of_life.oppdatering()
        # sett inn noe annet enn blankt eller 'q', og du får en ny mulighet for input
        kommando = input('Du satt inn en ukjent kommando\nTrykk enter for aa fortsette. Skriv inn q og trykk enter for aa avslutte. ')
main()

