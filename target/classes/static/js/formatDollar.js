console.log('LOADED SCRIPT');
const formatDollar = () => {
    console.log('ADJUSTING PRICES');
    Array.from(document.querySelectorAll(".currency")).map(price => {
        const formattedDollar = Intl.NumberFormat("en-US", {
            style: "currency",
            currency: "USD",
            minimumFractionDigits: 2,
        }).format(price.innerText);

        price.innerText = formattedDollar;
    });
    console.log('FINISHED ADJUSTING PRICES');
};

formatDollar();