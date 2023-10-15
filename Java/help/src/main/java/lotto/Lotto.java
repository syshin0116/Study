package lotto;
import java.util.Arrays;
import java.util.Random;

public class Lotto {
    private int[] mLottoNumber = new int[6];
    private boolean mIsValid;

    // 생성자 (인자 있음)
    public Lotto(int... numbers) {
        if (numbers.length != 6) {
            mIsValid = false;
            return;
        }
        for (int i = 0; i < 6; i++) {
            mLottoNumber[i] = numbers[i];
        }
        mIsValid = verify();
    }

    // 생성자 (인자 없음)
    public Lotto() {
        generate();
    }

    private void generate() {
        Random rand = new Random();
        int idx = 0;

        while (idx < 6) {
            int n = rand.nextInt(45) + 1;
            if (!contains(n)) {
                mLottoNumber[idx++] = n;
            }
        }
        mIsValid = true; // 자동 생성은 항상 유효함
    }

    private boolean contains(int n) {
        for (int i = 0; i < 6; i++) {
            if (mLottoNumber[i] == n) {
                return true;
            }
        }
        return false;
    }

    private boolean verify() {
        for (int i = 0; i < 6; i++) {
            if (mLottoNumber[i] < 1 || mLottoNumber[i] > 45) {
                return false;
            }
            for (int j = i + 1; j < 6; j++) {
                if (mLottoNumber[i] == mLottoNumber[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void show() {
        if (mIsValid) {
            for (int i = 0; i < 6; i++) {
                System.out.print(mLottoNumber[i] + " ");
            }
            System.out.println();
        } else {
            System.out.println("로또 번호 오류: " + Arrays.toString(mLottoNumber));
        }
    }


    public int[] getNumbers() {
        if (mIsValid) {
            return mLottoNumber;
        } else {
            return null;
        }
    }
}
