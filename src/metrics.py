from sklearn.metrics import accuracy_score, precision_score, recall_score, roc_auc_score

def print_metrics(y_test, y_pred):
#y_test -- test (expected) labels
#y_pred -- predicted (actual) class
    print('\naccuracy: ')
    print(accuracy_score(y_test, y_pred))
    print('\nprecision: ')
    print(precision_score(y_test, y_pred))
    print('\nrecall: ')
    print(recall_score(y_test, y_pred))

def print_roc_auc(y_test, y_prob):
#y_test -- test (expected) labels
#y_prob -- prediction probability of 1 (true) class
    print('\nroc auc: ')
    print(roc_auc_score(y_test, y_prob))

def print_fnr_fpr(y_test, y_pred):
#y_test -- test (expected) labels
#y_pred -- predicted (actual) class
    fnr, fpr = calc_fnr_fpr(y_test, y_pred)
    print('\nFNR: ')
    print(fnr)
    print('\nFPR: ')
    print(fpr)

def calc_fnr_fpr(y_target, y_prediction):
#y_target -- test (expected) labels
#y_prediction -- predicted (actual) class
    fp = 0
    fn = 0
    n = len(y_target)
    for i in range(n):
        if y_prediction[i] == 1 and y_target[i] == 0:
            fp += 1
        if y_prediction[i] == 0 and y_target[i] == 1:
            fn += 1
    return (fn / n, fp / n)

def print_all_metrics(y_test, y_prob, threshold=0.5):
#y_test -- test (expected) labels
#y_prob -- prediction probability of 1 (true) class
    y_pred = [int(y >= threshold) for y in y_prob]
    print_fnr_fpr(y_test, y_pred)
    print_metrics(y_test, y_pred)
    print_roc_auc(y_test, y_prob)