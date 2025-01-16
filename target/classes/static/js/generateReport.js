const button = document.querySelector('#downloadCsv');

button.addEventListener('click', async (event) => {
    // const url =
    console.log('button hit!')
    event.preventDefault();
    const queryParameters = new URLSearchParams(window.location.search);
    const timeframe = queryParameters.get('timeframe');
    const acceptedValues = ['7', '14', '30', '60', '180', '365'];

    if (timeframe && (acceptedValues.filter(item => item === timeframe).length != 1)) {
        handleToast("Tsk tsk, you can't do that!", 'haha');
        return;
    }

    const reqUrl = '/report/purchases/download' + '?timeframe=' + timeframe;
    const response = await fetch(reqUrl);

    if (!response.ok) {
        handleToast('Error while downloading!', 'error');
        return;
    }

    const data = await response.json();
    console.log('data from endpoint', data);

    if (data) {
        const csvData = await convertDataToCSV(data)
        await downloadCSV(csvData);
        handleToast('Download Successful', 'success');
        return;
    }
    handleToast('Nothing to download!', 'warning');
})


function handleToast(message, status) {
    const toast = document.querySelector('#toast');
    const toastMessage = document.querySelector('#toastMessage');
    toastMessage.textContent = message;

    let classList = 'fixed bottom-5 right-5 drop-shadow-lg text-white p-4 rounded-lg ';

    switch (status) {
        case 'success':
            classList += 'bg-green-400';
            break;
        case 'error':
            classList += 'bg-red-400';
            break;
        case 'notify':
            classList += 'bg-slate-800';
            break;
        default:
            classList += 'bg-yellow-500';
            break;
    }

    toast.className = classList;

    setTimeout(() => {
        toast.classList.add("hidden");
    }, 3500);
}

async function convertDataToCSV(data) {
    data.unshift(['Purchase ID', 'Name', 'Price', 'Sale Date']);
    data.unshift([]);
    data.unshift(['Purchases Report - ' + new Date().toLocaleString()]);
    return data.map(row => row.join(',')).join('\n');
}

function downloadCSV(csvData) {
    const blob = new Blob([csvData], {type: "text/csv;charset=utf-8"});
    const url = URL.createObjectURL(blob);
    const anchor = document.createElement("a");
    anchor.href = url;
    anchor.download = 'purchase-report.csv';
    document.body.appendChild(anchor);
    anchor.click();
    document.body.removeChild(anchor);
}
