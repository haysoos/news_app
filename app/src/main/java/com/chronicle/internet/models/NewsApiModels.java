package com.chronicle.internet.models;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class NewsApiModels {

    private final Subject<Headlines> mHeadlinesSubject;

    private NewsApiModels() {
        mHeadlinesSubject = BehaviorSubject.create();
    }

    public static NewsApiModels create() {
        return new NewsApiModels();
    }

    public Observable<Headlines> headlines() {
        return mHeadlinesSubject.hide();
    }

    public void emitHeadlines(Headlines headlines) {
        mHeadlinesSubject.onNext(headlines);
    }
}
