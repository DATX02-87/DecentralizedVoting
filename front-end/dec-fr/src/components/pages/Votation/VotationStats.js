import React, { useState, useEffect } from 'react';
import { Spinner } from 'react-bootstrap';
import { getElectionStatistics } from 'services/api';
import { BarChart, Bar, Tooltip, XAxis, YAxis, ResponsiveContainer} from 'recharts';

const VotationStats = ({votationName}) => {
    const [data, setData] = useState(undefined);

    useEffect(() => {
        getElectionStatistics(votationName).then(statistics => setData(statistics));
    }, [votationName]);
    if (!data) {
        return <Spinner />;
    }
    return (
        <ResponsiveContainer height={250} width="100%">
            <BarChart data={data}>
                <Tooltip />
                <YAxis allowDecimals={false} />
                <XAxis dataKey='name' />
                <Bar dataKey='votes' fill='#82ca9d'/>
            </BarChart>
        </ResponsiveContainer>
    );
};

export default VotationStats;