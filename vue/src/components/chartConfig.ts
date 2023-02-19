import Service from '../services/ServerService.js'

async function getPoints() {
    const response = await Service.getPoints(7);
    return response.data;
  }
  
  async function main() {
    const points = await getPoints();
    console.log(points);
    data.datasets[0].data = [points, 30];
    
  }
  
  
  main();

export const data = {
    labels: [
      'You',
      'Max'
    
    ],
    datasets: [
      {
        label: 'Points Today' ,
        backgroundColor: '#f87979',
        data: []
      }
    ]
  }
  
  export const options = {
    responsive: true,
    maintainAspectRatio: false
  }