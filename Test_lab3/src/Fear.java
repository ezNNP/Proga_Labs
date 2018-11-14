public enum Fear {
    CALM() {
        public int square(int a) {
            return a*a;
        }
    },
    USUAL,
    ANXIETY,
    FOBIA,
    HORROR,
    SHOCK;
}
