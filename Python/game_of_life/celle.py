class Celle: 
    
    def __init__(self, lever=False):
        self._lever = lever
    
    # Endre staus
    def settDoed(self):
        self._lever = False

    def settLevende(self):
        self._lever = True
    
    # Hent staus
    def erLevende(self):
        if self._lever:
            return True
        return False
    
    # Henter statustegn
    def hentStatusTegn(self):
        if self._lever:
            return 'O'
        return '.'
