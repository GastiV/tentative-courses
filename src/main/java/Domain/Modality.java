package Domain;

public enum Modality{
    GROUP(){
        @Override
        public Integer maximumSize() {
            return 6;
        }
    },
    INDIVIDUAL();

    public Integer maximumSize() {
        return 1;
    }
}

