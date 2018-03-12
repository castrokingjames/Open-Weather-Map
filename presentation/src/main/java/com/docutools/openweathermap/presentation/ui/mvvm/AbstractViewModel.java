package com.docutools.openweathermap.presentation.ui.mvvm;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AbstractViewModel
        extends ViewModel {

    protected CompositeDisposable disposables = new CompositeDisposable();

    protected Scheduler subscriber;
    protected Scheduler observer;

    public AbstractViewModel() {
        this(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    public AbstractViewModel(Scheduler subscriber, Scheduler observer) {
        this.subscriber = subscriber;
        this.observer = observer;
    }

    protected <T> Single<T> scheduler(Single<T> single) {
        return single.subscribeOn(subscriber)
                .observeOn(observer);
    }

    protected Completable scheduler(Completable completable) {
        return completable.subscribeOn(subscriber)
                .observeOn(observer);
    }

    protected <T> Maybe<T> scheduler(Maybe<T> maybe) {
        return maybe.subscribeOn(subscriber)
                .observeOn(observer);
    }

    protected <T> Flowable<T> scheduler(Flowable<T> flowable) {
        return flowable.subscribeOn(subscriber)
                .observeOn(observer);
    }

    protected <T> Observable<T> scheduler(Observable<T> observable) {
        return observable.subscribeOn(subscriber)
                .observeOn(observer);
    }

    public void clear() {
        disposables.clear();
    }

    @Override
    protected void onCleared() {
        clear();
    }
}