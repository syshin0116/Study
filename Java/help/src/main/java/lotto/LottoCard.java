package lotto;

public class LottoCard {
    private Lotto[] mLottos;
    private int mNum;

    public LottoCard(int num) {
        mNum = (num > 5) ? 5 : num; // 최대 5개까지만
        mLottos = new Lotto[mNum];
        for (int i = 0; i < mNum; i++) {
            mLottos[i] = new Lotto(); // 초기화
        }
    }

    public boolean auto(int idx) {
        if (idx >= mNum || idx < 0) return false;
        mLottos[idx] = new Lotto();
        return true;
    }

    public boolean manual(int idx, int... numbers) {
        if (idx >= mNum || idx < 0 || numbers.length != 6) return false;
        mLottos[idx] = new Lotto(numbers);
        return true;
    }

    public int[] get(int idx) {
        if (idx >= mNum || idx < 0) return null;
        return mLottos[idx].getNumbers();
    }

    public void show() {
        for (int i = 0; i < mNum; i++) {
            if (mLottos[i].getNumbers() == null) {
                System.out.println(">> 복권" + (i + 1) + "에 잘못된 번호가 설정됨!");
            }
            System.out.print("복권" + (i + 1) + ": ");
            mLottos[i].show();
        }
    }

}
